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
	
	var actionSheet = new Ext.ActionSheet(
        {
            hideOnMaskTap : true,
            stretchX : true,
            stretchY : true,
            enter : 'right',
            exit : 'right',
            layout : 'fit',
            width : 230,
            style :
            {
                background : '#fff',
                boxShadow : '0 0 10px #666',
                webkitBoxShadow : '0 0 10px #666',
            },
            items : [{text:'sadfsafsasffdsafsafsafsdafsfsfsaffdafdas'}]
        });


		var horizontalCarousel = Ext.Viewport.add({
			height:500,
			xtype: 'carousel',
			direction: 'horizontal',
			items:cards,
			listeners:{
				activate:function() {
					initAnim();
				}
			}
		});
	
	}
});