package com.mpaike.bot.service.impl;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;

import com.mpaike.bot.model.WebUrl;
import com.mpaike.bot.service.IWebUrlService;
import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.sys.service.impl.BaseService;

public class WebUrlService extends BaseService implements IWebUrlService {

	@Override
	public void save(WebUrl bean) {

		super.getWebUrlDao().saveOrUpdate(bean);

	}

	@Override
	public WebUrl find(Long id) {

		return super.getWebUrlDao().get(id);

	}

	@Override
	public List<WebUrl> find(Pagination p, OrderBy ob) {

		return super.getWebUrlDao().findAllPagination(p, ob);

	}

	@Override
	public boolean startWebSpider(Long id) {
		WebUrl webUrl = this.find(id);
		if(webUrl!=null){
			webUrl.setStatus(WebUrl.START);
			this.save(webUrl);
			this.getBotSpider().startSpider(webUrl.getUrl(), webUrl.getEnName(), webUrl.getThreadNum(),webUrl.getType(),webUrl.getWidth(),webUrl.getHeight());
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean stopWebSpider(Long id) {
		WebUrl webUrl = this.find(id);
		if(webUrl!=null){
			webUrl.setStatus(WebUrl.STOP);
			this.save(webUrl);
			this.getBotSpider().haltSpider(webUrl.getUrl());
			return true;
		}else{
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public List spiderLog(String url){
		if(url!=null){
			return this.getBotSpider().spiderLog(url);
		}else{
			return EMPTY_LIST;
		}
	}

	@Override
	public List<WebUrl> findList(Long status) {
		return this.getWebUrlDao().findByProperty("status", status);
	}

}