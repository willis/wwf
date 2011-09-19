package com.mpaike.core.util.page;

import java.util.List;

import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

@SuppressWarnings("serial")
public class Pagination extends SimplePage implements java.io.Serializable,
		Paginable {
	
	private static JsonConfig jsonConfig = new JsonConfig();
	static{
		jsonConfig.setExcludes(new String[]{"list"});
	}

	public Pagination() {
	}
	
	public Pagination(int pageSize) {
		super(pageSize);
	}

	public Pagination(int pageNo, int pageSize, int totalCount) {
		super(pageNo, pageSize, totalCount);
	}

	@SuppressWarnings("unchecked")
	public Pagination(int pageNo, int pageSize, int totalCount, List list) {
		super(pageNo, pageSize, totalCount);
		this.list = list;
	}

	public int getFirstResult() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 当前页的数据
	 */
	@SuppressWarnings("unchecked")
	private List list;

	@SuppressWarnings("unchecked")
	public List getList() {
		return list;
	}

	@SuppressWarnings("unchecked")
	public void setList(List list) {
		this.list = list;
	}
	
	public String toString(){
		return JSONSerializer.toJSON(this, jsonConfig).toString();
	}
}
