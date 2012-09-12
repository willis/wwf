package com.mpaike.upload.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.sys.service.impl.BaseService;
import com.mpaike.upload.model.Annex;
import com.mpaike.upload.service.AnnexService;

public class AnnexServiceImpl extends BaseService implements AnnexService {

	@Override
	public void save(Annex annex) {
		this.getAnnexDao().saveOrUpdate(annex);
		
	}

	@Override
	public List<Annex> find(String object_id, String type, Pagination p,
			OrderBy ob) {
		
		return this.getAnnexDao().find(object_id, type, p, ob);
	}

	@Override
	public List<Annex> find(String object_id, String type) {
	
		return this.getAnnexDao().find(object_id, type);
	}

	@Override
	public void remove(Annex annex) {
		this.getAnnexDao().delete(annex);
		
	}

	@Override
	public void remove(Long id) {
		this.getAnnexDao().deleteById(id);
		
	}

	@Override
	public Annex get(Long id) {
		
		return this.getAnnexDao().get(id);
	}

	@Override
	public int saveBatch(String object_id, String real_id, String type) {
		
		return this.getAnnexDao().saveBatch(object_id, real_id, type);
	}

	@Override
	public List<Annex> findByObject_id(String object_id, String type) {
	
		return this.getAnnexDao().findByObject_id(object_id, type);
	}

	
}

