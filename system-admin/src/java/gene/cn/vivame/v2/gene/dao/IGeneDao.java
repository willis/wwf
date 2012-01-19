package cn.vivame.v2.gene.dao;

import java.util.List;
import java.util.Set;

import cn.vivame.v2.gene.model.Tag;
import cn.vivame.v2.gene.model.TagRelation;

public interface IGeneDao{
	
	public boolean addTagRelation(TagRelation tagRelation);
	
	public Set<TagRelation> getChildTagRelation(String tagName);
	
	public Set<TagRelation> getParentTagRelation(String tagName);
	
	public void removeRelation(TagRelation tagRelation);
	
	public void saveTag(Tag tag);
	
	public void removeTag(Tag tag);
	
	public void removeAllChildRelation(Tag tag);
	
	public Tag getTag(String name);
	
	public void removeAll();
	
	public Tag[] getTags(String... tags);
	
	public List<String> getCommendTag(int tagModel);
	
	public void saveCommendTag(List<String> list, int tagModel);
	
}
