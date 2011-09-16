package com.mpaike.bot.action;

import java.util.List;

import com.mpaike.bot.model.WebUrl;
import com.mpaike.util.app.BaseAction;

public class WebUrlAction extends BaseAction{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2884893102617198833L;

	public void list(){
		List<WebUrl> datas = this.getWebUrlService().find(this.pageInfo,this.getOrderby());
		this.printPageList(datas);
	}

	
}
