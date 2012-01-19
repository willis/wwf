package cn.vivame.v2.gene.dao;

import java.util.List;
import java.util.Set;

import cn.vivame.v2.gene.model.Tag;
import cn.vivame.v2.gene.model.TagRelation;

import com.mpaike.core.database.hibernate.BaseDao;
import com.mpaike.core.util.page.Pagination;

public interface IGeneSqlDao extends BaseDao<Tag>{
	
	public List<Tag> findTagList(String name,Pagination pager);
	
	public List<Tag> likeTag(String likeName);
	
	public List<Tag> findTag(String name,Pagination pager);
	
	public List<Tag> findSubscribeTag(String name, int tagModel, int commendType, Pagination pager) ;
	
	public List<Tag> findCommendTag(int tagModel,Pagination pager) ;
	
	public List<Tag> findCommendTag(int tagModel) ;
	
	public void saveTagAlias(String tagName,Set<String> aliasSet);
	
	public void saveCommendTagAlias(Tag tag);
	public void saveCancelCommendTagAlias(Tag tag);
	
	public void saveTagRelation(TagRelation relation);
	
	public void removeTagRelation(TagRelation relation);
	
	public void saveTag(Tag tag);
	
	public void removeTag(Tag tag);
	
	public void removeTagChild(Tag tag);
	
	public void removeAllTag();
	
	public Tag findTag(String name);
	
	public List<Tag> findTagList(Pagination pager);

}
