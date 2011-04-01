package cn.com.icore.upload.service;

import java.util.List;

import com.fins.gt.model.PageInfo;

import cn.com.icore.upload.model.Annex;

/**
 * 附件的方法
 * 
 * @author Chen.H
 * 
 */
public interface AnnexService {
	public static final String ID_Name = "annexService";

	/**
	 * 保存附件
	 * 
	 */
	public void save(Annex annex);

	/**
	 * 查询方法
	 */
	public List<Annex> find(String object_id, String type, PageInfo page);

	/**
	 * 查询记录数
	 * 
	 * @return
	 */
	public int mycount(String object_id, String type);

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

	public void saveBatch(String object_id, String real_id);

	/**
	 * 根据object_id去查
	 * 
	 * @param object_id
	 * @return
	 */
	public List<Annex> findByObject_id(String object_id);
}
