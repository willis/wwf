package cn.vivame.v2.gene.dao;

import java.util.List;

import cn.vivame.v2.gene.model.SubscribeTag;

import com.mpaike.core.util.page.Pagination;

public interface ISubscribeTagDao {
	
	public SubscribeTag findSubscribeTag(String name);
	
	public SubscribeTag findDisplayName(String displayName);
	
	public void saveOrUpdateSubscribeTag(SubscribeTag ct);
	
	public void settingTypeNameNull(Long typeId);
	
	public void removeSubscribeTag(SubscribeTag ct);
	
	public void removeAll();
	
	public List<SubscribeTag> findSubscribeTagList(String name,int tagModel,Pagination pager);
	
	public List<SubscribeTag> findSubscribeTagList(Long typeId,String name,int tagModel,Pagination pager);
	
	public List<SubscribeTag> findCommendList(int tagModel,long typeId);
	
	public List<SubscribeTag> findAllSubscribeTag();
	
	public void updateSort(String tagName,int sort);

}
