package cn.vivame.v2.gene.dao.impl;

import java.util.Map;

import redis.clients.jedis.ShardedJedisPool;
import cn.vivame.v2.gene.dao.IUserGeneDao;
import cn.vivame.v2.gene.model.SubscribeTag;

import com.mpaike.util.dao.redis.KeyValueDao;

public class UserGeneDao implements IUserGeneDao {
	
	public static final String GENE_SETTING = "_GS_";
	private static final String WEBSITE_SUBSCRIBE = "_WS_SUBSCRIBE_";
	public static final String SETTING_NAME = "WeighRate";
	
	private KeyValueDao keyValueDao;
	
	public UserGeneDao(ShardedJedisPool shardedJedisPool){
		keyValueDao = new KeyValueDao(shardedJedisPool);
	}

	public Map<String,Double> findWeighRateMap() {
		return (Map)keyValueDao.get(GENE_SETTING,SETTING_NAME);
	}

	public void saveWeighRate(Map<String,Double> map) {
		String[] keys = (String[])map.keySet().toArray(new String[0]);
		for(String key : keys){
			keyValueDao.put(GENE_SETTING, SETTING_NAME, map);
		}
		
	}
	
	@Override
	public void saveSubscribeTag(SubscribeTag st) {
		if(st!=null){
			keyValueDao.put(WEBSITE_SUBSCRIBE, st.getTagName().toLowerCase(), st);
		}
	}

	@Override
	public void removeSubscribeTag(String name) {
		if(name!=null){
			keyValueDao.remove(WEBSITE_SUBSCRIBE, name.toLowerCase());
		}
	}

	@Override
	public SubscribeTag getSubscribeTag(String name) {
		if(name!=null){
			return (SubscribeTag)keyValueDao.get(WEBSITE_SUBSCRIBE, name.toLowerCase());
		}
		return null;
	}

	@Override
	public void removeSubscribeTagAll() {
		keyValueDao.remove(WEBSITE_SUBSCRIBE);
	}


}
