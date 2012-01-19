package cn.vivame.v2.gene.dao;

import java.util.List;

import cn.vivame.v2.gene.model.TagCatalog;

import com.mpaike.core.database.hibernate.BaseDao;
import com.mpaike.core.util.page.Pagination;

public interface ITagCatalogDao extends BaseDao<TagCatalog> {
	
	public TagCatalog findTagCatalog(Long id);
	
	public TagCatalog findTagCatalog(String name,int model);
	
	public TagCatalog findTagCatalogForTagName(String tagName);
	
	public void saveTagCatalog(TagCatalog tc);
	
	public void mergeTagCatalog(TagCatalog tc);
	
	public List<TagCatalog> findTagCatalogList(int tagModel,Pagination pager) ;
	
	public List<TagCatalog> findCommendCatalogList(int tagModel,Pagination pager) ;
	
	public void saveCommendSort(long id,int sort);
}
