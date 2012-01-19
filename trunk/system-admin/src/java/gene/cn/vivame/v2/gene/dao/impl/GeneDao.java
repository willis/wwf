package cn.vivame.v2.gene.dao.impl;

import static java.util.Collections.EMPTY_SET;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import cn.vivame.v2.gene.dao.IGeneDao;
import cn.vivame.v2.gene.model.Tag;
import cn.vivame.v2.gene.model.TagRelation;

import com.mpaike.util.dao.redis.KeyValueDao;

public class GeneDao extends KeyValueDao implements IGeneDao{
	
	private static String TAG = "GENE";
	private static final String ISSUE_SUBSCRIBE = "_issue_subscribe_";
	private static final String ISSUE_COMMEND = "_issue_commend_";
	private static final String ALIAS = "alias_";

	public GeneDao(ShardedJedisPool shardedJedisPool,String dbName) {
		super(shardedJedisPool);
		TAG = dbName;
	}
	
	
	
	public boolean addTagRelation(TagRelation tagRelation){ 
		tagRelation.setParentTagName(tagRelation.getParentTagName().trim());
		tagRelation.setTagName(tagRelation.getTagName().trim());
		Tag parentTag = getTag(tagRelation.getParentTagName());
		Tag tag = getTag(tagRelation.getTagName());
		if(parentTag!=null&&tag!=null){
			tagRelation.setParentTagName(parentTag.getName());
			tagRelation.setTagName(tag.getName());
			//判断关系允许类型
			if(parentTag.getType()==Tag.TYPE_CONTAINER||parentTag.getType()==Tag.TYPE_INSTANCE_CONTAINER){
				if(tag.getType()==Tag.TYPE_CONTAINER||tag.getType()==Tag.TYPE_INSTANCE_CONTAINER){
					//不允许容器套容器
					return false;
				}else{
					//如果父类是实例化容器，关系只能为实例化
					if(parentTag.getType()==Tag.TYPE_INSTANCE_CONTAINER){
						tagRelation.setRelationType(TagRelation.RELATION_INSTANCE);
					}
					parentTag.getRelationSet().remove(tagRelation);
					parentTag.getRelationSet().add(tagRelation);
					tag.getParentRelationSet().remove(tagRelation);
					tag.getParentRelationSet().add(tagRelation);
					putTag(parentTag);
					putTag(tag);
					return true;
				}
			}else{

				if(tag.getType()==Tag.TYPE_CONTAINER||tag.getType()==Tag.TYPE_INSTANCE_CONTAINER){
					//容器只有一个父节点，清除唯一父节点
					Tag tempTag;
					for(TagRelation tr : tag.getParentRelationSet()){
						tempTag = getTag(tr.getParentTagName());
						tempTag.getRelationSet().remove(tr);
						putTag(tempTag);
					}
					parentTag.getRelationSet().remove(tagRelation);
					parentTag.getRelationSet().add(tagRelation);
					tag.getParentRelationSet().clear();
					tag.getParentRelationSet().add(tagRelation);
					putTag(parentTag);
					putTag(tag);
				}else{
					//去除关联，再添加关联
					parentTag.getRelationSet().remove(tagRelation);
					parentTag.getRelationSet().add(tagRelation);
					tag.getParentRelationSet().remove(tagRelation);
					tag.getParentRelationSet().add(tagRelation);
					putTag(parentTag);
					putTag(tag);
				}

				return true;
			}

		}else{
			return false;
		}
	}
	
	public Set<TagRelation> getChildTagRelation(String tagName){
		Tag tag = getTag(tagName);
		if(tag==null){
			return EMPTY_SET;
		}
		return tag.getRelationSet();
	}
	
	public Set<TagRelation> getParentTagRelation(String tagName){
		Tag tag = getTag(tagName);
		if(tag==null){
			return EMPTY_SET;
		}
		return tag.getParentRelationSet();
	}
	
	public void removeRelation(TagRelation tagRelation){
		tagRelation.setParentTagName(tagRelation.getParentTagName().trim());
		tagRelation.setTagName(tagRelation.getTagName().trim());
		Tag parentTag = getTag(tagRelation.getParentTagName());
		Tag tag = getTag(tagRelation.getTagName());
		if(parentTag!=null&&tag!=null){
			tagRelation.setParentTagName(parentTag.getName());
			tagRelation.setTagName(tag.getName());
			parentTag.getRelationSet().remove(tagRelation);
			tag.getParentRelationSet().remove(tagRelation);
			putTag(parentTag);
			putTag(tag);
		}
	}
	
	public void removeAllChildRelation(Tag tag){
		tag.setName(tag.getName().trim());
		Tag t = getTag(tag.getName());
		if(t!=null){
			t.setRelationSet(new TreeSet<TagRelation>());
			putTag(t);
		}
	}
	
	public void saveTag(Tag tag){
		tag.setName(tag.getName().trim());
		Tag t = getTag(tag.getName());
		String name;
		if(t!=null&&t.getAlias()!=null&&t.getAlias().size()>0){
			Iterator<String> iter = t.getAlias().iterator();
			while(iter.hasNext()){
				name = iter.next().trim();
				remove(TAG,(ALIAS+name).toLowerCase());
			}
		}
		tag.getAlias().remove("");
		Set<String> aliasSet = new HashSet<String>();
		Set<String> alias = tag.getAlias();
		
		if(alias!=null&&alias.size()>0){
			Iterator<String> iter = alias.iterator();
			while(iter.hasNext()){
				name = iter.next().trim();
				if(!name.equals("")&&getTag(name)==null){
					put(TAG,(ALIAS+name).toLowerCase(),tag.getName());
					aliasSet.add(name);
				}
			}
		}
		tag.setAlias(aliasSet);
		//判断是否是来源标签
		if(tag.getName().startsWith("site:")){
			tag.setTagModel(Tag.TAGMODEL_ORIGIN);
		}else if(tag.getName().startsWith("addr:")){
			tag.setTagModel(Tag.TAGMODEL_ADDR);
		}else{
			tag.setTagModel(Tag.TAGMODEL_TAG);
		}
		putTag(tag);
	}

	
	public void removeTag(Tag tag){
		Set<String> alias = tag.getAlias();
		if(alias!=null&&alias.size()>0){
			Iterator<String> iter = alias.iterator();
			while(iter.hasNext()){
				remove(TAG,(ALIAS+iter.next().trim()).toLowerCase());
			}
		}
		Tag t = null;
		//移除子关联
		if(tag.getRelationSet().size()>0){
			for(TagRelation tr : tag.getRelationSet()){
				t = getTag(tr.getTagName());
				if(t!=null&&t.getParentRelationSet()!=null){
					t.getParentRelationSet().remove(tr);
				}
			}
		}
		//移除父关联
		if(tag.getParentRelationSet().size()>0){
			for(TagRelation tr : tag.getParentRelationSet()){
				t = getTag(tr.getParentTagName());
				if(t!=null&&t.getParentRelationSet()!=null){
					t.getParentRelationSet().remove(tr);
				}
				
			}
		}
		remove(TAG,tag.getName().trim().toLowerCase());
		
	}
	
	public Tag getTag(String name){
		name = name.trim().toLowerCase();
		Tag tag = (Tag)get(TAG,name);
		if(tag==null){
			String alias = (String)get(TAG,ALIAS+name);
			if(alias!=null){
				tag = (Tag)get(TAG,alias.toLowerCase());
			}
		}
		return tag;
	}
	
	public void putTag(Tag tag){
		put(TAG,tag.getName().toLowerCase(),tag);
	}

	public void removeAll() {
		remove(TAG);
	}
	
	public Tag[] getTags(String... tags){
		ShardedJedis jedis = shardedJedisPool.getResource();
		Object obj = null;
		List<byte[]> fieldsByte = new ArrayList<byte[]>();
		for(String field : tags){
			fieldsByte.add(field.trim().toLowerCase().getBytes(charset));
		}
		List<byte[]> lists = jedis.hmget(TAG.getBytes(charset), (byte[][])fieldsByte.toArray());
		shardedJedisPool.returnResource(jedis);
		List<Object> objList = new ArrayList<Object>();
		for(byte[] bytes : lists){
			objList.add(bytesToObject(bytes));
		}
		return (Tag[])objList.toArray();
	}

	public List<String> getCommendTag(int tagModel) { 
		return (List<String>)get(TAG,ISSUE_COMMEND+tagModel);
	}

	public void saveCommendTag(List<String> list, int tagModel) {
		put(TAG,ISSUE_COMMEND+tagModel,list);
	}

 
}
