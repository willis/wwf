package cn.vivame.v2.gene.dao;

import java.util.Map;

import cn.vivame.v2.gene.model.RelationCount;

import com.mpaike.core.database.hibernate.BaseDao;

public interface IGeneSettingDao extends BaseDao<RelationCount>{
	
	public void saveSetting(Map<String,Double> map);
	
	public Map<String,Double> getSettingMap();

}
