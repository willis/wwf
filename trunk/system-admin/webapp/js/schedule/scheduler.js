/*
Copyright DHTMLX LTD. http://www.dhtmlx.com
You allowed to use this component or parts of it under GPL terms
To use it on other terms please contact us at sales@dhtmlx.com
*/
window.dhtmlXScheduler=window.scheduler={};
scheduler.init=function(id,date,mode){
	date=date||(new Date());
	mode=mode||"week";
	
	this._obj=(typeof id == "string")?document.getElementById(id):id;
	this._els=[];
	this._scroll=true;
	this._quirks=(_isIE && document.compatMode == "BackCompat");
	this._quirks7=(_isIE && navigator.appVersion.indexOf("MSIE 8")==-1);
	
	dhtmlxEventable(this);
	
	this.init_templates();
	this.get_elements()		
	this.set_actions();
	dhtmlxEvent(window,"resize",function(){
		window.clearTimeout(scheduler._resize_timer);
		scheduler._resize_timer=window.setTimeout(function(){
			scheduler.update_view();
		}, 100);
	})
	
	this.set_sizes();
	this.setCurrentView(date,mode);
}
scheduler.xy={
	nav_height:22,
	scale_left:50,
	scroll_width:18,
	scale_height:20
}
scheduler.set_sizes=function(){
	var w = this._x = this._obj.clientWidth;
	var h = this._y = this._obj.clientHeight;
	
	//not-table mode always has scroll - need to be fixed in future
	var scale_x=this._table_view?0:(this.xy.scale_left+this.xy.scroll_width);
	var scale_s=this._table_view?-1:this.xy.scale_left;
	var data_y=this.xy.scale_height+this.xy.nav_height+(this._quirks?-2:0);
	
	this.set_xy(this._els["dhx_cal_navline"][0],w,this.xy.nav_height,0,0);
	this.set_xy(this._els["dhx_cal_header"][0],w-scale_x,this.xy.scale_height,scale_s,this.xy.nav_height+(this._quirks?-1:1));
	this.set_xy(this._els["dhx_cal_data"][0],w,h-(data_y+2),0,data_y+2);
}
scheduler.set_xy=function(node,w,h,x,y){
	node.style.width=w+"px";
	node.style.height=h+"px";
	if (arguments.length>3){
		node.style.left=x+"px";
		node.style.top=y+"px";	
	}
}
scheduler.get_elements=function(){
	//get all child elements as named hash
	var els=this._obj.getElementsByTagName("DIV");
	for (var i=0; i < els.length; i++){
		var name=els[i].className;
		if (!this._els[name]) this._els[name]=[];
		this._els[name].push(els[i]);
		
		//check if name need to be changed
		var t=scheduler.locale.labels[els[i].getAttribute("name")||name];
		if (t) els[i].innerHTML=t;
	}
}
scheduler.set_actions=function(){
	for (var a in this._els)
		if (this._click[a])
			for (var i=0; i < this._els[a].length; i++)
				this._els[a][i].onclick=scheduler._click[a];
	this._obj.onselectstart=function(e){ return false; }
	this._obj.onmousemove=function(e){
		scheduler._on_mouse_move(e||event);
	}
	this._obj.onmousedown=function(e){
		scheduler._on_mouse_down(e||event);
	}
	this._obj.onmouseup=function(e){
		scheduler._on_mouse_up(e||event);
	}
	this._obj.ondblclick=function(e){
		scheduler._on_dbl_click(e||event);
	}
}
scheduler.select=function(id){
	if (this._table_view) return; //temporary block
	if (this._select_id==id) return;
	this.editStop(false);
	this.unselect();
	this._select_id = id;
	this.updateEvent(id);
}
scheduler.unselect=function(id){
	if (id && id!=this._select_id) return;
	var t=this._select_id;
	this._select_id = null;
	if (t) this.updateEvent(t);
}
scheduler._click={
	dhx_cal_data:function(e){
		var trg = e?e.target:event.srcElement;
		var id = scheduler._locate_event(trg);
		if (id) {
			scheduler.callEvent("onClick",[id,(e||event)]);
			scheduler.select(id);
			var mask = trg.className;
			if (mask.indexOf("_icon")!=-1)
				scheduler._click.buttons[mask.split(" ")[1].replace("icon_","")](id);
			} else if (new Date().valueOf()-(scheduler._new_event||0) > 500 && scheduler._edit_id){
				var c=scheduler.locale.labels.confirm_closing;
					if (!c || confirm(c))
						scheduler.editStop(scheduler.config.positive_closing);
				}
	},
	dhx_cal_prev_button:function(){
		scheduler.setCurrentView(scheduler.date.add(scheduler._date,-1,scheduler._mode));
	},
	dhx_cal_next_button:function(){
		scheduler.setCurrentView(scheduler.date.add(scheduler._date,1,scheduler._mode));
	},
	dhx_cal_today_button:function(){
		scheduler.setCurrentView(new Date());
	},
	dhx_cal_tab:function(){
		var mode = this.getAttribute("name").split("_")[0];
		scheduler.setCurrentView(scheduler._date,mode);
	},
	buttons:{
		"delete":function(id){ var c=scheduler.locale.labels.confirm_deleting; if (!c||confirm(c)) scheduler.deleteEvent(id); },
		edit:function(id){ scheduler.edit(id); },
		save:function(id){ scheduler.editStop(true); },
		details:function(id){ scheduler.showLightbox(id); },
		cancel:function(id){ scheduler.editStop(false); }
	}
}
scheduler._on_dbl_click=function(e){
	var src = e.target||e.srcElement;
	switch(src.className.split(" ")[0]){
		case "dhx_scale_holder":
		case "dhx_scale_holder_now":
		case "dhx_month_body":
			if (!scheduler.config.drag_create) break;
			var pos=this._mouse_coords(e);
			var start=this._min_date.valueOf()+(pos.y*this.config.time_step+(this._table_view?0:pos.x)*24*60)*60000;
			var end = start+this.config.time_step*60000;
			this._drag_id=this.uid();
			this._drag_mode="new-size";
			this._loading=true;
			this.addEvent(new Date(start), new Date(end),this.locale.labels.new_event,this._drag_id);		
			this._loading=false;
			this._on_mouse_up(e);		
			break;
		case "dhx_body":
			var id = this._locate_event(src);
			if (this.config.details_on_dblclick)
				this.showLightbox(id);
			else
				this.edit(id);
			break;
		case "dhx_cal_event_line":
		case "dhx_cal_event_clear":
			var id = this._locate_event(src);
			this.showLightbox(id);
			break;
	}
}
scheduler._mouse_coords=function(ev){
	var pos;
	if(ev.pageX || ev.pageY)
	    pos={x:ev.pageX, y:ev.pageY};
	else pos={
	    x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,
	    y:ev.clientY + document.body.scrollTop  - document.body.clientTop
	}
	//apply layout
	pos.x-=getAbsoluteLeft(this._obj)+(this._table_view?0:this.xy.scale_left);
	pos.y-=getAbsoluteTop(this._obj)+this.xy.nav_height+this.xy.scale_height-this._els["dhx_cal_data"][0].scrollTop;
	//transform to date
	if (!this._table_view){
		pos.x=Math.max(0,Math.ceil(pos.x/this._cols[0])-1);
		pos.y=Math.max(0,Math.ceil(pos.y*60/(this.config.time_step*this.config.hour_size_px))-1)+this.config.first_hour*(60/this.config.time_step);
	} else {
		
		pos.y=(Math.max(0,Math.ceil(pos.x/this._cols[0])-1)+Math.max(0,Math.ceil(pos.y/this._colsS.height)-1)*7)*24*60/this.config.time_step; 
		pos.x=0;
	}
	return pos;
}
scheduler._on_mouse_move=function(e){
	if (this._drag_mode){
		var pos=this._mouse_coords(e);
		if (!this._drag_pos || this._drag_pos.x!=pos.x || this._drag_pos.y!=pos.y){
			this._drag_pos=pos;
			
			if (this._drag_mode=="create"){
				this._loading=true; //will be ignored by dataprocessor
				
				var start=this._min_date.valueOf()+(pos.y*this.config.time_step+(this._table_view?0:pos.x)*24*60)*60000;
				if (!this._drag_start){
					this._drag_start=start; return; 
				}
				var end = start;
				if (end==this._drag_start) return;
				
				this._drag_id=this.uid();
				this.addEvent(new Date(this._drag_start), new Date(end),this.locale.labels.new_event,this._drag_id);
				this._loading=false;
				this._drag_mode="new-size";
				
			} 

			var ev=this.getEvent(this._drag_id);
			var start,end;
			if (this._drag_mode=="move"){
				start = this._min_date.valueOf()+(pos.y*this.config.time_step+pos.x*24*60)*60000;
				end = ev.end_date.valueOf()-(ev.start_date.valueOf()-start);
			} else {
				start = ev.start_date.valueOf();
				if (this._table_view)
					end = this._min_date.valueOf()+(pos.y+(24*60/this.config.hour_size_px))*this.config.time_step*60000;
				else
					end = this.date.date_part(ev.end_date).valueOf()+pos.y*this.config.time_step*60000;
				if (this._drag_mode == "new-size"){ 
					if (end <= this._drag_start){
						start = end;
						end = this._drag_start;
					} 
				} else if (end<=start) 
					end=start+this.config.time_step*60000;
			}
			
			ev.start_date=new Date(start);
			ev.end_date=new Date(end);
			this.updateEvent(this._drag_id);
		}
	}
}
scheduler._on_mouse_down=function(e){
	var src = e.target||e.srcElement;
		switch(src.className.split(" ")[0]){
		case "dhx_cal_event_line":
			this._drag_mode="move"; //item in table mode
			break;
		case "dhx_header":
		case "dhx_title":
			this._drag_mode="move"; //item in table mode
			break;
		case "dhx_footer":
			this._drag_mode="resize"; //item in table mode
			break;
		case "dhx_scale_holder":
		case "dhx_scale_holder_now":
		case "dhx_month_body":
			this._drag_mode="create";
			break;
		default:
			this._drag_mode=null;
			this._drag_id=null;
	}
	if (this._drag_mode){
		if (!this.config["drag_"+this._drag_mode])
			this._drag_mode=this._drag_id=0;
		else {
			this._drag_id=this._locate_event(src);
			this._drag_event=this._copy_event(this.getEvent(this._drag_id)||{});
		}
	}
	this._drag_start=null;
}
scheduler._on_mouse_up=function(e){
	if (this._drag_mode && this._drag_id){
		//drop
		var ev=this.getEvent(this._drag_id);
		if (!this._drag_event.start_date || ev.start_date.valueOf()!=this._drag_event.start_date.valueOf() || ev.end_date.valueOf()!=this._drag_event.end_date.valueOf()){
			var is_new=(this._drag_mode=="new-size");
			if (is_new && this.config.edit_on_create){
				this.unselect();
				this._new_event=new Date();//timestamp of creation
				if (this._table_view || this.config.edit_on_create_details) {
					this._drag_mode=null;
					return this.showLightbox(this._drag_id);
				}
				this._select_id=this._edit_id=this._drag_id;
			}else
				this.callEvent(is_new?"onEventAdded":"onEventChanged",[this._drag_id,this.getEvent(this._drag_id)]);
			this.render_view_data();
		}
	}
	this._drag_mode=null;
}
scheduler.update_view=function(){
	this.set_sizes();
	this._reset_scale();
	if (this._load_mode && !this._loaded[this._load_format(this._load_date(this._date))]){
		this._load();
	} else
		this.render_view_data();
}
scheduler.setCurrentView=function(date,mode){
	this._mode=mode||this._mode;
	this._date=date;
	this._table_view=(this._mode=="month");
	
	var tabs=this._els["dhx_cal_tab"];
	for (var i=0; i < tabs.length; i++) {
		tabs[i].className="dhx_cal_tab"+((tabs[i].getAttribute("name")==this._mode+"_tab")?" active":"");
	};
	
	this.update_view();
}
scheduler._reset_scale=function(){
	var count=(this._mode=="day"?1:7);
	var h=this._els["dhx_cal_header"][0];
	var b=this._els["dhx_cal_data"][0];
	
	h.innerHTML="";
	b.innerHTML="";
		
	this._cols=[];	//store for data section
	this.set_sizes();
	var summ=parseInt(h.style.width); //border delta
	var left=0;
	
	var d,dd,sd,today;
	dd=this.date[this._mode+"_start"](new Date(this._date.valueOf()));
	d=sd=this._table_view?scheduler.date.week_start(dd):dd;
	today=this.date.date_part(new Date());
	
	//reset date in header
	var ed=scheduler.date.add(dd,1,this._mode);
	this._els["dhx_cal_date"][0].innerHTML=this.templates[this._mode+"_date"](dd,ed,this._mode);
	
	this._min_date=d;
	for (var i=0; i<count; i++){
		this._cols[i]=Math.floor(summ/(count-i));
	
		//header scale	
		var c=document.createElement("DIV"); c.className="dhx_scale_bar";
		this.set_xy(c,this._cols[i]-1,20,left,0);//-1 for border
		c.innerHTML=this.templates[this._mode+"_scale_date"](d,this._mode); //TODO - move in separate method
		h.appendChild(c);
		
		if (!this._table_view){
			var c=document.createElement("DIV"); c.className="dhx_scale_holder";
			if (d.valueOf()==today.valueOf()) c.className="dhx_scale_holder_now";
			else c.className="dhx_scale_holder";
			this.set_xy(c,this._cols[i]-1,this.config.hour_size_px*(this.config.last_hour-this.config.first_hour),left+51,0);//-1 for border
			b.appendChild(c);
		}
		
		d=this.date.add(d,1,"day")
		summ-=this._cols[i];
		left+=this._cols[i];
	}
	this._max_date=d;
	
	if (this._table_view)
		this._reset_month_scale(b,dd,sd);
	else
		this._reset_hours_scale(b,dd,sd);
}
scheduler._reset_hours_scale=function(b,dd,sd){
	var c=document.createElement("DIV");
	c.className="dhx_scale_holder";
	
	var date = new Date(1980,1,1,this.config.first_hour,0,0);
	for (var i=this.config.first_hour; i < this.config.last_hour; i++) {
		var cc=document.createElement("DIV");
		cc.className="dhx_scale_hour";
		cc.style.height=this.config.hour_size_px-(this._quirks?0:1)+"px";
		cc.innerHTML=scheduler.templates.hour_scale(date);
		
		c.appendChild(cc);
		date=this.date.add(date,1,"hour");
	};
	b.appendChild(c);
}
scheduler._reset_month_scale=function(b,dd,sd){
	var ed=scheduler.date.add(dd,1,"month");
	
	//trim time part for comparation reasons
	var cd=new Date();
	this.date.date_part(cd);
	this.date.date_part(sd);
	
	var rows=Math.ceil((ed.valueOf()-sd.valueOf())/(60*60*24*1000*7));
	var tdcss=[];
	var height=(Math.floor(b.clientHeight/rows)-22);
	
	this._colsS={height:height+22};
	for (var i=0; i<=7; i++){
		tdcss[i]=" style='height:"+height+"px; width:"+((this._cols[i]||0)-1)+"px;' "
		this._colsS[i]=(this._cols[i-1]||0)+(this._colsS[i-1]||0);
	}
	
	
	
	
	this._min_date=sd;
	var html="<table cellpadding='0' cellspacing='0'>";
	for (var i=0; i<rows; i++){
		html+="<tr>";
			for (var j=0; j<7; j++){
				html+="<td";
				if (sd<dd)
					html+=" class='dhx_before' ";
				else if (sd>=ed)
					html+=" class='dhx_after' ";
				else if (sd.valueOf()==cd.valueOf())
					html+=" class='dhx_now' ";
				html+="><div class='dhx_month_head'>"+this.templates.month_day(sd)+"</div><div class='dhx_month_body' "+tdcss[j]+"></div></td>"
				sd=this.date.add(sd,1,"day");
			}
		html+="</tr>";
	}
	html+="</table>";
	this._max_date=sd;
	
	b.innerHTML=html;	
}