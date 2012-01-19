package cn.vivame.v2.gene.dao.impl;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.ShardedJedisPool;
import cn.vivame.v2.gene.dao.IPicGeneDao;
import cn.vivame.v2.gene.model.GeneGrade;

import com.mpaike.image.model.Picture;
import com.mpaike.util.dao.redis.KeyValueDao;

public class PicGeneDao implements IPicGeneDao {
	
	public static final String PIC_GENE = "_PG_";
	public static final String PIC_OBJECT = "_PO_";
	public static final String GENE_SETTING = "_GS_";
	public static final String SETTING_NAME = "RelationRate";
	private KeyValueDao keyValueDao;
	
	public PicGeneDao(ShardedJedisPool shardedJedisPool) throws ClassNotFoundException{
		keyValueDao = new KeyValueDao(shardedJedisPool);
	}
	
	public void savePicGeneGrade(Long artId, List<GeneGrade> list) {
		keyValueDao.put(PIC_GENE, String.valueOf(artId), list);
	}
	
	public void savePicCache(Picture pic){
		keyValueDao.put(PIC_OBJECT, String.valueOf(pic.getId()), pic);
	}
	
	public void removePicCache(String picId){
		keyValueDao.remove(PIC_OBJECT, picId);
	}

	public List<GeneGrade> findGeneGrade(Long artId) {
		return (List<GeneGrade>)keyValueDao.get(PIC_GENE, String.valueOf(artId));
	}

	public Map<String,Double> findRelationRateMap() {
		return (Map)keyValueDao.get(GENE_SETTING,SETTING_NAME);
	}

	public void saveRelationRate(Map<String,Double> map) {
		String[] keys = (String[])map.keySet().toArray(new String[0]);
		for(String key : keys){
			keyValueDao.put(GENE_SETTING, SETTING_NAME, map);
		}
		
	}

}
