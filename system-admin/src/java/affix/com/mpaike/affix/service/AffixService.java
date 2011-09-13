package com.mpaike.affix.service;

import java.util.List;

import com.mpaike.affix.model.Affix;

public interface AffixService {
	
	public static final String ID_NAME = "affixService";
	
	/**
	 * 添加附件
	 * @param affix
	 */
	public abstract void addAffix(Affix affix);
	
	/**
	 * 删除附件
	 * @param id
	 */
	public abstract void delAffix(long id);
	
	/**
	 * 查询附件
	 * @param objectType
	 * @param objectId
	 * @return
	 */
	public abstract List<Affix> getAffixs(long objectType, String objectId);
	/**
	 * 按ID获取附件
	 * @param id
	 * @return
	 */
	public abstract Affix getAffix(long id);
	
	/**
	 * 更新附件id
	 * @param objectType
	 * @param oldObjId
	 * @param newObjId
	 */
	public void updateAffixId(long objectType,String oldObjId,String newObjId );
	
	/**
	 * 更新附件点击率
	 * @param id
	 */
	public void updateAffixHotAdd(long id );
	
	/**
	 * 删除指定对象的附件
	 * @author zhangchong
	 * 2011-8-16
	 * @description 
	 * @param type
	 * @param objId
	 */
	public void deleteByTypeAndOBjId(Integer type,String objId);

}