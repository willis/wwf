package cn.vivame.v2.gene.dao.impl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import cn.vivame.v2.gene.dao.IGeneSqlDao;
import cn.vivame.v2.gene.model.Tag;
import cn.vivame.v2.gene.model.TagAlias;
import cn.vivame.v2.gene.model.TagRelation;

import com.mpaike.core.database.hibernate.BaseDaoImpl;
import com.mpaike.core.util.page.Pagination;

public class GeneSqlDao extends BaseDaoImpl<Tag> implements IGeneSqlDao{

	public List<Tag> likeTag(String likeName) {
		List<Tag> list = find("from Tag where type=1  and lower(name) like lower(?) escape '/' order by name",new Object[]{likeName+"%"},8,1);
		return list;
	}
	
	public List<Tag> findTag(String name,Pagination pager) {
		List<Tag> list = null;
		if(name==null){
			 list = find("from Tag where type=1 ","from Tag where (type=1 or type=3)  order by name",null,pager);
		}else{
			 list = find("from Tag where type=1 and lower(name) like lower(?) escape '/'","from Tag where type=1 and lower(name) like lower(?) escape '/' order by name",new Object[]{"%"+name+"%"},pager);	
		}
		return list;
	}
	
	public List<Tag> findSubscribeTag(String name, int tagModel, int commendType, Pagination pager) {
		List<Tag> list = null;
		if(name==null){
			list=find("from Tag where type=1 and tagSelected=1 and tagModel=?","from Tag where type=1  and tagSelected=1 and tagModel=?  order by name",new Object[]{tagModel},pager);
		}else{
			list=find("from Tag where type=1 and tagSelected=1 and tagModel=?  and lower(name) like lower(?) escape '/' ","from Tag where type=1 and tagSelected=1 and tagModel=? and lower(name) like lower(?) escape '/' order by name",new Object[]{tagModel,"%"+name+"%"},pager);
		}
		return list;
	}
	
	public List<Tag> findCommendTag(int tagModel,Pagination pager) {
		List<Tag> list = null;
		StringBuilder sb = new StringBuilder();
		sb.append("from Tag where type=1 and tagSelected=1 and commend=1 and tagModel=");
		sb.append(tagModel);
		list = find(sb.toString(),sb.append(" order by name").toString(),null,pager);
		return list;
	}
	
	public List<Tag> findCommendTag(int tagModel) {
		List<Tag> list = null;
		StringBuilder sb = new StringBuilder();
		sb.append("from Tag where type=1 and tagSelected=1 and commend=1 and tagModel=");
		sb.append(tagModel).append(" order by name");
		list = find(sb.toString());
		return list;
	}
	
	@Override
	public void saveCommendTagAlias(Tag tag) {
//		Set<String> alias = tag.getAlias();
//		if(alias!=null&&alias.size()>0){
//			TagAlias ta = null;
//			for(String name : alias){
//				ta = new TagAlias();
//				ta.setTagAliasName(name);
//				ta.setTagName(tag.getName());
//				ta.setCommend(1);
//				getHibernateTemplate().merge(ta);
//			}
//		}
		updateAndDelBeanForSQL("update TagAlias set commend=1 where lower(tagName) = lower(?)", new Object[]{tag.getName()});
	}
	
	@Override
	public void saveCancelCommendTagAlias(Tag tag) {
//		Set<String> alias = tag.getAlias();
//		if(alias!=null&&alias.size()>0){
//			TagAlias ta = null;
//			for(String name : alias){
//				ta = new TagAlias();
//				ta.setTagAliasName(name);
//				ta.setTagName(tag.getName());
//				ta.setCommend(1);
//				getHibernateTemplate().merge(ta);
//			}
//		}
		updateAndDelBeanForSQL("update TagAlias set commend=0 where lower(tagName) = lower(?)", new Object[]{tag.getName()});
	}
	
	public void saveTagAlias(String tagName,Set<String> aliasSet) {
		updateAndDelBeanForSQL("delete from TagAlias where lower(tagName) = lower(?)", new Object[]{tagName});
		TagAlias tagAlias = null;
		aliasSet.remove("");
		for(String a : aliasSet){
				tagAlias = new TagAlias();
				tagAlias.setTagAliasName(a);
				tagAlias.setTagName(tagName);
				merge(tagAlias);
		}
	}

	public void saveTagRelation(TagRelation relation) {
		try{
			merge(relation);
		}catch(Exception ex){
			System.out.println("saveTagRelation="+relation);
			ex.printStackTrace();
		}
		
	}
	
	public void saveTag(Tag tag){
		merge(tag);
	}

	public void removeTag(Tag tag) {
//		getHibernateTemplate().execute(new HibernateCallback() {
//		
//		@Override
//		public Object doInHibernate(Session session) throws HibernateException,
//				SQLException {
//			session.delete("delete from TagRelation where parentTagName = ?", new Object[]{tag.getName()});
//			session.delete("delete from TagRelation where tagName = ?", new Object[]{tag.getName()});
//			session.delete("delete from TagAlias where tagName = ?", new Object[]{tag.getName()});
//			return null;
//		}
//	});
		System.out.print("TagRelation1:"+updateAndDelBeanForSQL("delete from TagRelation where parentTagName = ?", new Object[]{tag.getName()}));
		System.out.print("TagRelation2:"+updateAndDelBeanForSQL("delete from TagRelation where tagName = ?", new Object[]{tag.getName()}));
		System.out.print("TagAlias:"+updateAndDelBeanForSQL("delete from TagAlias where tagName = ?", new Object[]{tag.getName()}));
		this.delete(tag);
	}

	@Override
	public void removeTagRelation(TagRelation relation) {
		delete(relation);
	}
	
	public void removeChildTag(Tag tag) {
		System.out.print("TagRelation1:"+updateAndDelBeanForSQL("delete from TagRelation where parentTagName = ?", new Object[]{tag.getName()}));
		System.out.print("TagAlias:"+updateAndDelBeanForSQL("delete from TagAlias where tagName = ?", new Object[]{tag.getName()}));
	}

	public void removeAllTag() {
		updateAndDelBeanForSQL("delete from TagAlias");
		updateAndDelBeanForSQL("delete from TagRelation");
		updateAndDelBeanForSQL("delete from Tag");
	}

	public List<Tag> findTagList(String name,Pagination pager) {
		List<Tag> list = null;
		if(name==null){
			 list = find("from Tag","from Tag order by name",null,pager);
		}else{
			 list = find("from Tag where lower(name) like lower(?) escape '/'","from Tag where lower(name) like lower(?) escape '/' order by name",new Object[]{"%"+name+"%"},pager);	
		}
		return list;
	}

	@Override
	public void removeTagChild(Tag tag) {
		// TODO Auto-generated method stub
		
	}
	
	public Set<String> findAliasList(String name){
		List<TagAlias> list = null;
		StringBuilder sb = new StringBuilder();
		Pagination pager = new Pagination();
		pager.setPageSize(100);
		sb.append("from TagAlias where lower(tagName) = lower(?)");
		list = find(sb.toString(),sb.toString(),new Object[]{name},pager);
		
		return convTagAlias(list);
	}
	
	
	public List<Tag> findTagList(Pagination pager){
		List<Tag> list = null;
		list = find("from Tag","from Tag order by name",null,pager);
		return list;
	}
	
	public Tag findTag(String name){
		Object obj = this.get(name);
		if(obj!=null){
			Set<String> list = findAliasList(name);
			Tag tag = (Tag)obj;
			tag.setAlias(list);
			Pagination paper = new Pagination();
			paper.setPageSize(Integer.MAX_VALUE);
			List<TagRelation> childList = find("from TagRelation where parentTagName = ?","from TagRelation where parentTagName=? order by tagName",new Object[]{name},paper);
			tag.setRelationSet(convTagRelation(childList));
			Pagination paper1 = new Pagination();
			paper1.setPageSize(Integer.MAX_VALUE);
			List<TagRelation> parentList = find("from TagRelation where tagName = ?","from TagRelation where tagName=? order by parentTagName",new Object[]{name},paper1);
			tag.setParentRelationSet(convTagRelation(parentList));
			
			return tag;
		}
		return null;
	}

	
	private static Set<String> convTagAlias(List<TagAlias> list){
		Set<String> alias = new TreeSet<String>();
		if(list!=null){
			for(TagAlias ta : list){
				alias.add(ta.getTagAliasName());
			}
		}
		return alias;
	}
	
	private static Set<TagRelation> convTagRelation(List<TagRelation> list){
		Set<TagRelation> trSet = new TreeSet<TagRelation>();
		if(list!=null){
			for(TagRelation tr : list){
				trSet.add(tr);
			}
		}
		return trSet;
	}




}
