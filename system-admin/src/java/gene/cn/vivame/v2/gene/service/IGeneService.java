package cn.vivame.v2.gene.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.vivame.v2.gene.model.GeneGrade;
import cn.vivame.v2.gene.model.SelectedTag;
import cn.vivame.v2.gene.model.SubscribeTag;
import cn.vivame.v2.gene.model.Tag;
import cn.vivame.v2.gene.model.TagCatalog;
import cn.vivame.v2.gene.model.TagRelation;

import com.mpaike.core.exception.FormatErrorException;
import com.mpaike.core.exception.ParameterException;
import com.mpaike.core.util.page.Pagination;

public interface IGeneService {
	
	public boolean addTagRelation(TagRelation tagRelation) throws ParameterException;
	
	public TagRelation findTagRelation(String parentName,String name) throws ParameterException;
	
	public Set<TagRelation> getChildTagRelation(String tagName)throws ParameterException;
	
	public Set<TagRelation> getParentTagRelation(String tagName)throws ParameterException;
	
	public void remove(TagRelation tagRelation)throws ParameterException;
	
	public void saveTagAndRelation(Tag tag,boolean saveCommend,TagRelation tagRelation)throws ParameterException;
	
	public void saveTag(Tag tag,boolean saveCommend,boolean alias)throws ParameterException;
	
	public void removeTag(Tag tag)throws ParameterException;
	
	public void removeTagChild(Tag tag)throws ParameterException;
	
	public void removeAll();
	
	public boolean hasTag(String tagName)throws ParameterException;
	
	public Tag getTag(String name) throws ParameterException;
	
	//public Map<String,Tag> getTagAllChildMap(String tagName)throws ParameterException;
	
	public boolean hasName(String name,String parentName)throws ParameterException;
	
	public boolean hasParentName(String name,String parentContainName) throws ParameterException;
	
	public List<String> notAddAlias(String tagName,Set<String> alias);
	
	public List<GeneGrade> grade(String... tagsName);
	
	public void saveGradeRedis(Long artId,List<GeneGrade> list);
	
	public List<String> getTagPaths(String tagName,String s,List<String> fullList) throws ParameterException;
	
	public Map<String,Tag> getTags(String... tags);
	
	public  List<Tag> saveTagForFile(String filepath,int type,int semproperty,int dingyue, int tuijian,String parentName,int relationType) throws ParameterException,FormatErrorException;
	
	public List<TagRelation> saveRelationForFile(String filepath) throws ParameterException,FormatErrorException;
	
	public List<Tag> likeTag(String likename)throws ParameterException;
	
	public List<Tag> findTag(String name,Pagination pager)throws ParameterException;
	
	public List<Tag> findTagList(String name,Pagination pager);
	
	public void saveRelationSetting(Map<String,Double> map)throws ParameterException;
	
	public Map<String,Double> getMapRelationSetting();
	
	public void saveWeighSetting(Map<String,Double> map)throws ParameterException;
	
	public Map<String,Double> getMapWeighSetting();

	public List<SubscribeTag> findSubscribeTag(String name,int tagModel,Pagination pager);
	
	public List<SubscribeTag> findSubscribeTag(Long typeId,String name,int tagModel,Pagination pager);
	
	public void removeSubscribeTag(String name) throws ParameterException;
	
	public void saveSubscribeTags(String parentName,List<SelectedTag> tags) throws ParameterException;
	
	public void saveSubscribeTag(String name,boolean select,boolean special,int type,String displayName,Long typeId,File iconFile,File coverFile)throws ParameterException;
	
	public void saveCommend(String name,boolean select) throws ParameterException;
	
	public void cacheAllSubscribeTag();
	
	public TagCatalog findTagCatalog(Long id);
	
	public TagCatalog findTagCatalog(String name,int model);
	
	public List<TagCatalog> findTagCatalogList(int tagModel,Pagination pager);
	
	public List<TagCatalog> findCommendCatalogList(int tagModel,Pagination pager);
	
	public void saveTagCatalog(TagCatalog tc)throws ParameterException;
	
	public void removeTagCatalog(TagCatalog tc);
	
	public List<SubscribeTag> findCommendList(int tagModel,long catalogId);
	
	public void saveCommendSort(String tagName,int sort);
	
	public void saveCommendCatalogSort(long id,int sort);
	
	public Tag findSqlTag(String name);
	
	public void tagSqlToRedis() throws ParameterException;
	
}
