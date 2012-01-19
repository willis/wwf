package cn.vivame.v2.gene.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.vivame.v2.gene.dao.IGeneSettingDao;
import cn.vivame.v2.gene.model.RelationCount;

import com.mpaike.core.database.hibernate.BaseDaoImpl;

public class GeneSettingDao extends BaseDaoImpl<RelationCount> implements IGeneSettingDao {

	public void saveSetting(Map<String,Double> map){
		String[] keys = (String[])map.keySet().toArray(new String[0]);
		for(String key : keys){
			this.save(new RelationCount(key,map.get(key)));
		}
	}
	
	public Map<String,Double> getSettingMap(){
		Map<String,Double> map = new HashMap<String,Double>();
		List<RelationCount> list = this.find("from RelationCount");
		for(RelationCount rc : list){
			map.put(rc.getKeyName(), rc.getValue());
		}
		return map;
	}
}
