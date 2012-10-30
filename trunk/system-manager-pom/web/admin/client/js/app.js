Ext.setup({
    tabletStartupScreen: 'tablet_startup.png',
    phoneStartupScreen: 'phone_startup.png',
    icon: 'icon.png',
    glossOnIcon: false,
    onReady: function(){
		
		var cards = [];
		var imgData = Ext.get('imgdata');
	
		for(var i=0,n=imgData.getAttribute('title');i<n;i++){
			cards[i] = {contentEl :Ext.get('card'+i)};
		}
		
				var dom;
		var imgArray = Ext.DomQuery.select('img','card0');
		//var imgArray = Ext.select('img','imgdata');
		
		function initAnim() {
							if(imgArray){
								var dur = 400;
								var d =0;
								for(var i=0,n=imgArray.length;i<n;i++){
									Ext.Anim.run(imgArray[i],Ext.anims.fade,{duration: dur,out:false,delay:d})
									d+=120;
									imgArray.onclick = function(){
										Ext.Viewport.add(actionSheet.show());
									}
								}
							}
						};
	

			var appMain = Ext.create('Ext.Container', {
					layout:{
							 type: 'card',
							 animation: {
								 type: 'slide',
								 //direction: 'left',
								 duration: 250
							 }
					},
					items: [
						{
							height:600,
							xtype: 'carousel',
							direction: 'horizontal',
							items:cards
						},
						{
							contentEl:Ext.get('tags')
						}
					]
			});
			
			var viewPort = Ext.Viewport.add(appMain);
			
		btn4.onclick = function(){
			var test =appMain.getLayout();
			//alert(appMain.getLayout());
			appMain.setActiveItem(1);
		}
		btn2.onclick = function(){
			//alert(appMain.items.length);
			appMain.setActiveItem(0);
		}
	
	}
});