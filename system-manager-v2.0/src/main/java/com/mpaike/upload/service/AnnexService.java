package com.mpaike.upload.service;

import java.util.List;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.upload.model.Annex;


/**
 * 附件的方法
 * 
 * @author Chen.H
 * 
 */
public interface AnnexService {
	public static final String ID_NAME = "annexService";

	/**
	 * 保存附件
	 * 
	 */
	public void save(Annex annex);

	/**
	 * 查询方法
	 */
	public List<Annex> find(String object_id, String type, Pagination p, OrderBy ob);
	public List<Annex> find(String object_id, String type);


	/**
	 * 删除方法
	 */
	public void remove(Annex annex);
	/**
	 * 删除方法
	 */
	public void remove(Long id);

	/**
	 * 得到单个附件
	 * 
	 * @param id
	 * @return
	 */
	public Annex get(Long id);
	/**
	 * 更新附件
	 * 
	 * @param id
	 * @return
	 */
	public int saveBatch(String object_id, String real_id, String type);

	/**
	 * 根据object_id,type去查
	 * 
	 * @param object_id
	 * @return
	 */
	public List<Annex> findByObject_id(String object_id, String type);
}
