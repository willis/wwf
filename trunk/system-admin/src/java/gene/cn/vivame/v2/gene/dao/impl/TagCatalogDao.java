package cn.vivame.v2.gene.dao.impl;

import java.util.List;

import cn.vivame.v2.gene.dao.ITagCatalogDao;
import cn.vivame.v2.gene.model.TagCatalog;

import com.mpaike.core.database.hibernate.BaseDaoImpl;
import com.mpaike.core.database.hibernate.SequenceManager;
import com.mpaike.core.util.page.Pagination;

public class TagCatalogDao extends BaseDaoImpl<TagCatalog> implements ITagCatalogDao{

	public List<TagCatalog> findTagCatalogList(int tagModel,Pagination pager) {
		return find("from TagCatalog where tagModel=?","from TagCatalog where tagModel=? order by sortValue desc", new Object[]{tagModel}, pager);
	}

	public void saveTagCatalog(TagCatalog tc) {
		if(tc.getId()==null){
			tc.setId(SequenceManager.nextID(100));
		}
		this.save(tc);
	}

	@Override
	public TagCatalog findTagCatalog(String name,int model) {
		List<TagCatalog> list = find("from TagCatalog where name=? and tagModel=?",new Object[]{name,model});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public TagCatalog findTagCatalogForTagName(String tagName) {
		List<TagCatalog> list = find("from TagCatalog where tagName=?",new Object[]{tagName});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public TagCatalog findTagCatalog(Long id) {
		return (TagCatalog)this.get(id);
	}

	@Override
	public void mergeTagCatalog(TagCatalog tc) {
		this.merge(tc);
	}

	@Override
	public List<TagCatalog> findCommendCatalogList(int tagModel, Pagination pager) {
		return find("from TagCatalog where commend=1 and tagModel=?","from TagCatalog where commend=1 and tagModel=? order by sortValue desc", new Object[]{tagModel}, pager);
	}

	@Override
	public void saveCommendSort(long id, int sort) {
		this.updateAndDelBeanForSQL("update TagCatalog set sortValue=? where id=?", new Object[]{sort,id});
	}


}
