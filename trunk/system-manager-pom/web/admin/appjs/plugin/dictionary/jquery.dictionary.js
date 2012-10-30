/****
 * 基于数据字典的级联菜单v1.0
 * author:tianbao
 * ***/
$.fn.dictionary = function(opts) {
	opts = $.extend({
				url : '/maxejo/manager/dictionary/listDictionary.do',
				data : {
					method : 'getDictionarysByParentId',
					id : '1'
				},
				defaultOp : [{// 默认的选择框
					name : '请选择',
					value : ''
				}],
				// defaultValue:9, //默认选中的ID
				// 用户选择回调
				callback : function(value) {
					//alert(value);
				},
				c1:"id",//列名1
				c2:"name",//列名2
				top : 0
			}, opts || {});

	return this.each(function() {
		
		var _s = $(this)[0]
		
		function loadAjaxData() {
			$.ajax({
				type : "POST",
				url : opts.url,
				data : opts.data,
				dataType : "json",
				success : function(datas) {
					_s.options.length = 0;
					for (var i = 0; i < opts.defaultOp.length; i++) {
						_s.options[_s.options.length] = new Option(
								opts.defaultOp[i].name, opts.defaultOp[i].value);
					}
					for (var i = 0; i < datas.length; i++) {
						var op = new Option(datas[i][opts.c2], datas[i][opts.c1]);
						_s.options[_s.options.length] = op;

						if (opts.defaultValue == datas[i][opts.c1]) {
							op.selected = true;
						}
					}
					$(_s).bind("change", function() {
								opts.callback(this.value)
							});
					if(opts.defaultValue){
						opts.callback(opts.defaultValue)
					}
				},
				error : function(response) {
					alert(response.responseText);
				}

			});

		}
		
		 
		function setOptsAttribute(key ,value){
			opts[key]=value
		}
			loadAjaxData();
	});
	

}