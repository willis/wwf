package cn.vivame.v2.gene.dao;

import java.util.List;
import java.util.Map;

import cn.vivame.v2.gene.model.GeneGrade;

public interface IPicGeneDao {
	
	public void savePicGeneGrade(Long picId, List<GeneGrade> list);
	
	public List<GeneGrade> findGeneGrade(Long picId);
	
	public void removePicCache(String picId);
	
	public Map<String,Double> findRelationRateMap();
	
	public void saveRelationRate(Map<String,Double> map);

}
