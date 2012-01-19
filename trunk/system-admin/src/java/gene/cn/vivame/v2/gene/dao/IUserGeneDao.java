package cn.vivame.v2.gene.dao;

import java.util.Map;

import cn.vivame.v2.gene.model.SubscribeTag;

public interface IUserGeneDao {
	
	public Map<String,Double> findWeighRateMap();
	
	public void saveWeighRate(Map<String,Double> map);
	
	public void saveSubscribeTag(SubscribeTag st);
	
	public SubscribeTag getSubscribeTag(String name);
	
	public void removeSubscribeTag(String name);
	
	public void removeSubscribeTagAll();

}
