package com.mpaike.bot.action;

import java.util.List;

import com.mpaike.bot.model.WebUrl;
import com.mpaike.util.app.BaseAction;

public class WebUrlAction extends BaseAction{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2884893102617198833L;
	private WebUrl webUrl;
	private Long id;
	/**
	 * 查询列表
	 */
	public void list(){
		List<WebUrl> datas = this.getWebUrlService().find(this.pageInfo,this.getOrderby());
		this.printPageList(datas);
	}
	/**
	 * @author Chen.H
	 * @serialData
	 * 
	 */
	public void remove(){
		
	}
	/**
	 * @author Chen.H
	 * @serialData
	 * 
	 */
	public String edit(){
		webUrl = this.getWebUrlService().find(id);
		return "edit";
	}
	/**
	 * @author Chen.H
	 * @serialData
	 * 
	 */
	public void save(){
		String result = "保存成功！";
		if(webUrl.getId()!=null){
			result = "修改成功！";
		}
		this.getWebUrlService().save(webUrl);
		super.printSuccessJson(result);
	}
	/**
	 * @author Chen.H
	 * @serialData
	 * 
	 */
	public void start(){
		String result = "启动成功！";
		webUrl = this.getWebUrlService().find(id);
		webUrl.setStatus(WebUrl.START);
		this.getWebUrlService().save(webUrl);
		super.printSuccessJson(result);
	}
	/**
	 * @author Chen.H
	 * @serialData
	 * 
	 */
	public void stop(){
		String result = "停止成功！";
		webUrl = this.getWebUrlService().find(id);
		webUrl.setStatus(WebUrl.STOP);
		this.getWebUrlService().save(webUrl);
		super.printSuccessJson(result);
	}
	
	public WebUrl getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(WebUrl webUrl) {
		this.webUrl = webUrl;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
