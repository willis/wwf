package cn.vivame.v2.gene.dao.impl;

import java.util.ArrayList;
import java.util.List;

import cn.vivame.v2.gene.dao.ISubscribeTagDao;
import cn.vivame.v2.gene.model.SubscribeTag;

import com.mpaike.core.database.hibernate.BaseDaoImpl;
import com.mpaike.core.util.page.Pagination;

public class SubscribeTagDao extends BaseDaoImpl<SubscribeTag> implements ISubscribeTagDao{

	public List<SubscribeTag> findSubscribeTagList(String name,int tagModel,Pagination pager) {
		List<SubscribeTag> list = null;
		if(name==null){
			list=findList("from SubscribeTag where tagModel=? order by createdDate desc", pager, new Object[]{tagModel});
		}else{
			list=findList("from SubscribeTag where tagModel=? and lower(tagName) like lower(?) escape '/' order by createdDate desc",pager,new Object[]{tagModel,"%"+name+"%"});
		}
		return list;
	}

	public void saveOrUpdateSubscribeTag(SubscribeTag ct) {
		this.save(ct);
	}

	public void removeSubscribeTag(SubscribeTag ct) {
		this.delete(ct);
	}

	public SubscribeTag findSubscribeTag(String name) {
		List list = this.find("from SubscribeTag where tagName=?",new Object[]{name});
		if(list!=null&&list.size()>0){
			return (SubscribeTag)list.get(0);
		}
		return null;
	}
	
	public SubscribeTag findDisplayName(String displayName) {
		List list = this.find("from SubscribeTag where lower(displayName) = lower(?) ",new Object[]{displayName});
		if(list!=null&&list.size()>0){
			return (SubscribeTag)list.get(0);
		}
		return null;
	}

	public List<SubscribeTag> findSubscribeTagList(Long typeId,String name, int tagModel, Pagination pager) {
		List<SubscribeTag> list = null;
		List paramList = new ArrayList();
		StringBuilder sb = new StringBuilder();
		sb.append("from SubscribeTag where tagModel=?");
		paramList.add(tagModel);
		if(typeId!=null&&typeId!=-1){
			sb.append(" and typeId=?");
			paramList.add(typeId);
		}
		if(name!=null){
			sb.append(" and lower(tagName) like lower(?) escape '/' "); 
			paramList.add("%"+name+"%");
		}
		list = this.findList(sb.append(" order by createdDate desc").toString(),pager,paramList.toArray());
		return list;
	}
	
	public List<SubscribeTag> findCommendList(int tagModel,long typeId) {
		List<SubscribeTag> list = null;
		list = this.find("from SubscribeTag where commend=1 and tagModel=? and typeId=? order by sortCode desc",new Object[]{tagModel,typeId});
		return list;
	}

	@Override
	public void settingTypeNameNull(Long typeId) {
		this.updateAndDelBeanForSQL("update SubscribeTag set typeId=0 where typeId=?", new Object[]{typeId});
	}

	@Override
	public void removeAll() {
		this.updateAndDelBeanForSQL("delete from SubscribeTag",null);
	}

	@Override
	public void updateSort(String tagName, int sort) {
		this.updateAndDelBeanForSQL("update SubscribeTag set sortCode=? where tagName=?", new Object[]{sort,tagName});
	}

	@Override
	public List<SubscribeTag> findAllSubscribeTag() {
		List list = this.find("from SubscribeTag");
		return list;
	}

}
