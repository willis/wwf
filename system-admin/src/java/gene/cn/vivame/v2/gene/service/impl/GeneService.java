package cn.vivame.v2.gene.service.impl;

import static cn.vivame.v2.gene.util.StringArrayUtils.cross;
import static java.util.Collections.EMPTY_MAP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.vivame.v2.gene.model.GeneGrade;
import cn.vivame.v2.gene.model.RelationCount;
import cn.vivame.v2.gene.model.SelectedTag;
import cn.vivame.v2.gene.model.SubscribeTag;
import cn.vivame.v2.gene.model.Tag;
import cn.vivame.v2.gene.model.TagCatalog;
import cn.vivame.v2.gene.model.TagRelation;
import cn.vivame.v2.gene.service.IGeneService;

import com.mpaike.core.exception.FormatErrorException;
import com.mpaike.core.exception.ParameterException;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.sys.service.impl.BaseService;
 
public class GeneService extends BaseService implements IGeneService{

	public boolean addTagRelation(TagRelation tagRelation) throws ParameterException{
		if(tagRelation==null){
			throw new ParameterException();
		}
		boolean result;
		//add redis
		result = getGeneDao().addTagRelation(tagRelation);
		if(result){
			//添加到数据库中
			getGeneSqlDao().saveTagRelation(tagRelation);
		}
		return result; 
	}

	public Set<TagRelation> getChildTagRelation(String tagName)throws ParameterException {
		if(tagName==null){
			throw new ParameterException();
		}
		return getGeneDao().getChildTagRelation(tagName);
	}

	public Set<TagRelation> getParentTagRelation(String tagName)throws ParameterException {
		if(tagName==null){
			throw new ParameterException();
		}
		return getGeneDao().getParentTagRelation(tagName);
	}

	public void remove(TagRelation tagRelation)throws ParameterException {
		if(tagRelation==null){
			throw new ParameterException();
		}
		//redis操作
		getGeneDao().removeRelation(tagRelation);
		//从数据库中移除关系
		getGeneSqlDao().removeTagRelation(tagRelation);
	}

	public boolean hasTag(String tagName)throws ParameterException {
		Tag tag = getGeneDao().getTag(tagName);
		if(tag==null){
			return false;
		}
		return true;
	}

	public void saveTagAndRelation(Tag tag,boolean saveCommend,TagRelation tagRelation)throws ParameterException {
		if(tag==null){
			throw new ParameterException();
		}
		Tag t = getGeneDao().getTag(tag.getName());
		if(t!=null){
			if(!tag.getName().trim().equals(t.getName())){
				tag.setName(t.getName().trim());
				tagRelation.setTagName(t.getName());
			}
			tag.setParentRelationSet(t.getParentRelationSet());
			tag.setRelationSet(t.getRelationSet());
			if(!saveCommend){
				tag.setCommend(t.getCommend());
				tag.setTagSelected(t.getTagSelected());
			}
		}
		
		//add redis
		getGeneDao().saveTag(tag);//处理了别名
		
		//add tagRelation
		addTagRelation(tagRelation);
		
		//保存到mysql数据库中
		getGeneSqlDao().saveTag(tag);
		getGeneSqlDao().saveTagAlias(tag.getName(), tag.getAlias());
		
		
		//处理子的关系类型
		if(tag.getType()==Tag.TYPE_CONTAINER){
			for(TagRelation r : tag.getRelationSet()){
				r.setRelationType(tagRelation.getRelationType());
				addTagRelation(r);
			}
		}else if(tag.getType()==Tag.TYPE_INSTANCE_CONTAINER){
			for(TagRelation r : tag.getRelationSet()){
				r.setRelationType(TagRelation.RELATION_INSTANCE);
				addTagRelation(r);
			}
		}
	}
	
	public void saveTag(Tag tag,boolean saveCommend,boolean alias) throws ParameterException {
		if(tag==null){
			throw new ParameterException();
		}
		Tag t = getGeneDao().getTag(tag.getName());
		if(t!=null){
			if(!tag.getName().equals(t.getName())){
				tag.setName(t.getName());
			}
			tag.setParentRelationSet(t.getParentRelationSet());
			tag.setRelationSet(t.getRelationSet());
			if(!saveCommend){
				tag.setCommend(t.getCommend());
				tag.setTagSelected(t.getTagSelected());
			}
		}

		//redis操作,包括处理掉别名
		getGeneDao().saveTag(tag);
		//保存到mysql数据库中
		getGeneSqlDao().saveTag(tag);
		if(alias){
			getGeneSqlDao().saveTagAlias(tag.getName(), tag.getAlias());
		}
		
	}

	public Tag getTag(String name) throws ParameterException{
		if(name==null){
			throw new ParameterException();
		}
		return getGeneDao().getTag(name);
	}
	
	public TagRelation findTagRelation(String parentName, String name) throws ParameterException {
		if(parentName==null||name==null){
			throw new ParameterException();
		}
		Tag tag = getGeneDao().getTag(parentName);
		Tag tagSource = getGeneDao().getTag(name);
		Iterator<TagRelation> relationIter = tag.getRelationSet().iterator();
		TagRelation relation = null;
		while(relationIter.hasNext()){
			relation = relationIter.next();
			if(relation.getTagName().equals(tagSource.getName())){
				return relation;
			}
		}
		return null;
	}


	
	public String[] getParentTags(String tagName,Set<String> tagsSet) throws ParameterException{
		if(tagName==null){
			throw new ParameterException();
		}
		Tag tag = getGeneDao().getTag(tagName);
		Iterator<TagRelation>  relationIter;
		String[] partents = null;
		if(tag!=null){
			tagsSet.add(tagName);
			TagRelation relation = null;
			relationIter = tag.getParentRelationSet().iterator();
			while(relationIter.hasNext()){
				relation = relationIter.next();
				partents = getParentTags(relation.getParentTagName(),tagsSet);
				for(String t : partents){
					tagsSet.add(t);
				}
			}
		}
		return (String[])tagsSet.toArray(new String[0]);
	}
	
	public void countParentTags(String tagName,Map<String,Double> countMap,Double count,Map<String,Double> fenMap) throws ParameterException{
		if(tagName==null){
			throw new ParameterException();
		}
		Tag tag = getGeneDao().getTag(tagName);
		Iterator<TagRelation>  relationIter;
		String[] partents = null;
		if(tag!=null){
			TagRelation relation = null;
			relationIter = tag.getParentRelationSet().iterator();
			if(count==-1){
				while(relationIter.hasNext()){
					relation = relationIter.next();
					count = relationCount(relation,fenMap);
					countMap.put(relation.getTagName(),count);
					countParentTags(relation.getParentTagName(), countMap,fenMap.get(RelationCount.TAG_OTHER),fenMap);
				}
			}else{
				while(relationIter.hasNext()){
					relation = relationIter.next();
					countMap.put(tagName,count);
					countParentTags(relation.getParentTagName(), countMap,count,fenMap);
				}
			}

		}
		
	}
	
	private static Double relationCount(TagRelation relation,Map<String,Double> map){
		switch (relation.getRelationType()) {
			case TagRelation.RELATION_CROSS:
				return map.get(RelationCount.GATHER_RELATION);
			case TagRelation.RELATION_GATHER:
				return map.get(RelationCount.GATHER_RELATION);
			case TagRelation.RELATION_INSTANCE:
				return map.get(RelationCount.INSTANCE_RELATION);
			case TagRelation.RELATION_LIMIT:
				return map.get(RelationCount.LIMIT_RELATION);
			case TagRelation.RELATION_RELEVANCE:
				return map.get(RelationCount.RELEVANCE_RELATION);
			case TagRelation.RELATION_SPECIES:
				return map.get(RelationCount.SPECIES_RELATION);
			default:
				return map.get(RelationCount.TAG_OTHER);
		}
		
	}
	
	public List<String> getTagPaths(String tagName,String s,List<String> fullList) throws ParameterException{
		if(tagName==null){
			throw new ParameterException();
		}
		Tag tag = getGeneDao().getTag(tagName);
		TagRelation[]  relationArray;
		if(Tag.TAG_ROOT.equals(tagName)){
			fullList.add(s);
			//tagsList.clear();
		}else{
			if(tag!=null){
				relationArray = tag.getParentRelationSet().toArray(new TagRelation[tag.getParentRelationSet().size()]);
				StringBuilder sb = null ;
				for(int i =0,n=relationArray.length;i<n;i++){
					//过滤只有文章标签链
					if(isArtTag(relationArray[i].getParentTagName())){
						sb = new StringBuilder();
						sb.append(s).append(";").append(relationArray[i].getParentTagName());
						getTagPaths(relationArray[i].getParentTagName(),sb.toString(),fullList);
					}
				}
			}
		}
		return fullList;
	}
	
	public List<GeneGrade> grade(String... tagsName){
		Map<String,Double> tagGrade = new HashMap<String,Double>();
		Map<String,Double> settingMap = getMapRelationSetting();
		String crossName = null;
		String[] paths = null;
		String[] t1 = null;
		String[] t2 = null;
		List<String> t1Paths = null;
		List<String> t2Paths = null;
		Tag tag=null;
		try {
			for(String tagName : tagsName){
				//必须是文章标签，否则只添加不遍历
				if(!isArtTag(tagName)){
					tagGrade.put(tagName, 0.5);
					continue;
				}
				t1Paths = this.getTagPaths(tagName, tagName, new ArrayList());
				if(t1Paths!=null&&t1Paths.size()>0){
					
					//预打分底层标签TAG_SELF
					tagGrade.put(tagName, settingMap.get(RelationCount.TAG_SELF));
					//该标签的所有父类标签都打分
					countParentTags(tagName,tagGrade,-1.0,settingMap);
					
					
					for(String t : tagsName){

						if(!tagName.equalsIgnoreCase(t)){
							//必须是文章标签，否则忽略
							if(!isArtTag(t)){
								continue;
							}
							//交叉的标签打分为CROSS_RELATION分
							t2Paths = getTagPaths(t, t, new ArrayList<String>());
							if(t2Paths!=null&&t2Paths.size()>0){
								for(int m=0,n=t1Paths.size();m<n;m++){
									paths = t1Paths.get(m).split(";");
									for(int k=0,h=t2Paths.size();k<h;k++){
										crossName = cross(paths,t2Paths.get(k).split(";"));
										if(crossName!=null){
												tagGrade.put(crossName, settingMap.get(RelationCount.CROSS_RELATION));
										}
									}
								}

							}
						}
					}
					//文章所打标签小于TAG_SELF分的一律改为TAG_SELF分
					if(tagGrade.get(tagName)==null||tagGrade.get(tagName)<settingMap.get(RelationCount.TAG_SELF)){
						tagGrade.put(tagName,settingMap.get(RelationCount.TAG_SELF));
					}
				}

				tagGrade.remove(Tag.TAG_ROOT);
				tagGrade.remove(Tag.TAG_TAG_ROOT);
				tagGrade.remove(Tag.TAG_ADDR_ROOT);
				tagGrade.remove(Tag.TAG_SITE_ROOT);
				//移除容器标签
				String[] gs = (String[])tagGrade.keySet().toArray(new String[0]);
				for(String g : gs){
					tag = getTag(g);
					if(tag==null||tag.getType()!=Tag.TYPE_INSTANCE){
						tagGrade.remove(g);
					}
				}
			}
		} catch (ParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<GeneGrade> list = new ArrayList<GeneGrade>();
		String[] gs = (String[])tagGrade.keySet().toArray(new String[0]);
		for(String g : gs){
			list.add(new GeneGrade(g, tagGrade.get(g)));
		}
		java.util.Collections.sort(list, new Comparator<GeneGrade>(){

			@Override
			public int compare(GeneGrade arg0, GeneGrade arg1) {
				double c0 = arg0.getCount();
				double c1 = arg1.getCount();
				if(c0>c1){
					return -1;
				}else if(c0==c1){
					return 0;
				}else{
					return 1;
				}
			}
		});
		
		return list;
	}
	
	
	
	public boolean hasParentName(String name,String parentContainName) throws ParameterException {
		if(parentContainName==null){
			throw new ParameterException();
		}
		Tag tag = getGeneDao().getTag(name);
		if(tag!=null){
			if(tag.getName().equalsIgnoreCase(parentContainName.trim())){
				return true;
			}
			TagRelation relation = null;
			boolean isParentTag = false;
			Iterator<TagRelation> relationIter = tag.getParentRelationSet().iterator();
			while(relationIter.hasNext()){
				relation = relationIter.next();
				if(relation.getParentTagName()==null||relation.getParentTagName().equals(Tag.TAG_TAG_ROOT)||relation.getParentTagName().equals(Tag.TAG_ADDR_ROOT)||relation.getParentTagName().equals(Tag.TAG_SITE_ROOT)||relation.getParentTagName().equals(Tag.TAG_ROOT)){
					isParentTag = false;
				}else if(relation.getParentTagName().equals(parentContainName)){
					isParentTag = true;
				}else{
					isParentTag = hasParentName(relation.getParentTagName(),parentContainName);
				}
				if(isParentTag){
					return true;
				}
			}
		}
		return false;
	}


	public boolean hasName(String name,String parentContainName) throws ParameterException {
		if(parentContainName==null){
			throw new ParameterException();
		}
		Tag tag = getGeneDao().getTag(name);
		if(tag!=null){
			if(tag.getName().equalsIgnoreCase(parentContainName.trim())){
				return true;
			}
			TagRelation relation = null;
			boolean isParentTag = false;
			Map<String,Tag> childMap = getTagAllChildMap(parentContainName);
			Iterator<TagRelation> relationIter = tag.getParentRelationSet().iterator();
			while(relationIter.hasNext()){
				relation = relationIter.next();
				if(relation.getParentTagName()==null||relation.getParentTagName().equals(Tag.TAG_TAG_ROOT)||relation.getParentTagName().equals(Tag.TAG_ADDR_ROOT)||relation.getParentTagName().equals(Tag.TAG_SITE_ROOT)||relation.getParentTagName().equals(Tag.TAG_ROOT)){
					isParentTag = false;
				}else if(relation.getParentTagName().equals(parentContainName)||childMap.get(tag.getName())!=null){
					isParentTag = true;
				}else{
					isParentTag = hasName(relation.getParentTagName(),parentContainName);
				}
				if(isParentTag){
					return true;
				}
			}
		}
		return false;
	}

	public Map<String, Tag> getTags(String... tags) {
		Map<String, Tag> map = EMPTY_MAP;
		if(tags.length>0){
			map = new HashMap<String, Tag>();
			Tag[] tagArray = getGeneDao().getTags(tags);
			for(Tag tag:tagArray){
				map.put(tag.getName(),tag);
			}
		}
		return map;
	}


	private Map<String, Tag> getTagAllChildMap(String tagName)
			throws ParameterException {
		if(tagName==null){
			throw new ParameterException();
		}
		Tag tag = getGeneDao().getTag(tagName);
		Map<String,Tag> map = new HashMap<String,Tag>();
		if(tag!=null){
			TagRelation relation;
			map.put(tag.getName(), tag);
			Iterator<TagRelation> relationIter = tag.getRelationSet().iterator();
			while(relationIter.hasNext()){
				relation = relationIter.next();
				map.putAll(getTagAllChildMap(relation.getTagName()));
			}
		}
		return map;
	}

	
	public Map<String, Tag> getTagAllParentMap(String tagName)
			throws ParameterException {
		if(tagName==null){
			throw new ParameterException();
		}
		Tag tag = getGeneDao().getTag(tagName);
		Map<String,Tag> map = new HashMap<String,Tag>();
		if(tag!=null){
			TagRelation relation;
			map.put(tag.getName(), tag);
			Iterator<TagRelation> relationIter = tag.getParentRelationSet().iterator();
			while(relationIter.hasNext()){
				relation = relationIter.next();
				map.putAll(getTagAllParentMap(relation.getParentTagName()));
			}
		}
		return map;
	}

	public List<Tag> saveTagForFile(String filepath, int type, int semproperty, int dingyue, int tuijian,
			String parentName, int relationType) throws ParameterException,FormatErrorException {
		if (filepath == null) {
			throw new ParameterException();
		}
		int recCount = 0;
		File file = new File(filepath);
		List<Tag> tagList = new ArrayList<Tag>();
		if (file.exists()) {

			FileInputStream fis = null;

			try {
				fis = new FileInputStream(file);

				HSSFWorkbook msExcel = new HSSFWorkbook(fis);
				HSSFSheet sheet = msExcel.getSheetAt(0);

				HSSFRow row;
				String name=null, alias=null;
				Tag tag;
				Tag t;
				String[] aliasArray;
				TagRelation tr;
				for (int i = 1, n = sheet.getLastRowNum(); i <= n; i++) {
					row = sheet.getRow(i);
					if (row != null) {
						if (row.getLastCellNum() > 0) {
							if (row.getCell(0) != null) {
								if(row.getCell(0).getCellType()==HSSFCell.CELL_TYPE_STRING ){
									name = row.getCell(0).getStringCellValue()
									.trim();
								}else if(row.getCell(0).getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
									//name = String.valueOf(row.getCell(0).getNumericCellValue());
								}
							}
							if (row.getCell(1) != null) {
								if(row.getCell(1).getCellType()==HSSFCell.CELL_TYPE_STRING ){
									alias = row.getCell(1).getStringCellValue()
									.trim();
								}else if(row.getCell(1).getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
									//alias = String.valueOf(row.getCell(1).getNumericCellValue());
								}
							}
							if (StringUtils.isNotBlank(name)) {
								tag = new Tag();
								tag.setName(name);
								if (StringUtils.isNotBlank(alias)) {
									alias = alias.replaceAll("；", ";");
									aliasArray = alias.split(";");
									for (String a : aliasArray) {
										tag.getAlias().add(a);
									}
								}
								tag.setType(type);
								tag.setSemProperty(semproperty);
								tag.setTagSelected(dingyue);
								tag.setCommend(tuijian);
								if("".equals(tag.getName().trim())||tag.getName().indexOf(";")!=-1||tag.getName().indexOf("；")!=-1||tag.getName().indexOf("%")!=-1){
									tagList.add(tag);
								}else{
									if(parentName.trim().equalsIgnoreCase(name.trim())){
										tagList.add(tag);
									} else{
										tr = new TagRelation(name,parentName, relationType);
										t = getTag(name);
										if(t!=null){
											if(t.getParentRelationSet().contains(tr)){
												saveTagAndRelation(tag, false, tr);
											}else{
												if(!hasName(parentName,name)){
													saveTagAndRelation(tag, false, tr);
												}else{
													tagList.add(tag);
												}
											}
										}else{
											saveTagAndRelation(tag, false, tr);
										}

									}
								}
							}
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return tagList;
	}

	public List<TagRelation> saveRelationForFile(String filepath) throws ParameterException,FormatErrorException {
		if(filepath==null){
			throw new ParameterException();
		}
		List<TagRelation> list = new ArrayList<TagRelation>();
		int recCount = 0;
		File file = new File(filepath);
		if(file.exists()){
		    String record = null;
		    
		    FileInputStream fis = null;
		    InputStreamReader isr = null;
		    BufferedReader br = null;
		    try {
		      fis = new FileInputStream(file);

				HSSFWorkbook msExcel = new HSSFWorkbook(fis);
				HSSFSheet sheet = msExcel.getSheetAt(0);

				HSSFRow row;
				String parentName=null, tagName=null,relationName=null;
				TagRelation relation;
				Tag tag;
				long startTime;
				for (int i = 1, n = sheet.getLastRowNum(); i <= n; i++) {
					startTime = System.currentTimeMillis();
					row = sheet.getRow(i);
					if (row != null) {
						if (row.getLastCellNum() > 2 ) {
							if (row.getCell(0) != null) {
								if(row.getCell(0).getCellType()==HSSFCell.CELL_TYPE_STRING ){
										parentName = row.getCell(0).getStringCellValue()
										.trim();
								}else if(row.getCell(0).getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
									//parentName = String.valueOf(row.getCell(0).getNumericCellValue());
								}
							}
							if (row.getCell(1) != null) {
								if(row.getCell(1).getCellType()==HSSFCell.CELL_TYPE_STRING ){
									tagName = row.getCell(1).getStringCellValue()
									.trim();
								}else if(row.getCell(1).getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
									//tagName = String.valueOf(row.getCell(1).getNumericCellValue());
								}
							}
							if (row.getCell(2) != null) {
								if(row.getCell(2).getCellType()==HSSFCell.CELL_TYPE_STRING ){
									relationName = row.getCell(2).getStringCellValue()
									.trim();
								}else if(row.getCell(2).getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
									//relationName = String.valueOf(row.getCell(2).getNumericCellValue());
								}
							}

							if (StringUtils.isNotBlank(parentName)
									&& StringUtils.isNotBlank(tagName)
									&& StringUtils.isNotBlank(relationName)) {
								relation = new TagRelation();
								relation.setParentTagName(parentName);
								relation.setTagName(tagName);
								relation.setRelationType(convRalationType(relationName));
								//System.out.print(i);System.out.print("	");System.out.println(relation);
								if (relation.getRelationType() == 0) {
									list.add(relation);
								} else {
									if (!relation.getParentTagName().trim().equalsIgnoreCase(
											relation.getTagName().trim())) {
										if (!hasName(
												relation.getParentTagName(),
												relation.getTagName())) {
											recCount++;
											tag = getTag(relation.getTagName());
											if(tag!=null){
												if (addTagRelation(relation)) {
													//处理子的关系类型
													if(tag.getType()==Tag.TYPE_CONTAINER){
														for(TagRelation r : tag.getRelationSet()){
															r.setRelationType(relation.getRelationType());
															addTagRelation(r);
//															System.out.print(relation);
//															System.out.print("			");
//															System.out.println(System.currentTimeMillis()-startTime);
														}
													}else if(tag.getType()==Tag.TYPE_INSTANCE_CONTAINER){
														for(TagRelation r : tag.getRelationSet()){
															r.setRelationType(TagRelation.RELATION_INSTANCE);
															addTagRelation(r);
//															System.out.print(relation);
//															System.out.print("			");
//															System.out.println(System.currentTimeMillis()-startTime);
														}
													}
												}else{
													list.add(relation);
												}
											}else{
												list.add(relation);
											}
											
										} else {
											list.add(relation);
										}
									} else {
										list.add(relation);
									}
								}
							}

						}
					}
				}

		   } catch (IOException e) {
			   e.printStackTrace();
		   }finally{
			   if(br!=null){
				   	try {
					   br.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   }
			   if(isr!=null){
				   	try {
				   		isr.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   }
			   if(fis!=null){
				   	try {
				   		fis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   }
		   }
		}
		return list;

	}
	
	private static final int convRalationType(String name){
		String type = name.trim();
		if("实例化".equals(type)){
			return TagRelation.RELATION_INSTANCE;
		}else if("聚集".equals(type)){
			return TagRelation.RELATION_GATHER;
		}else if("属种".equals(type)){ 
			return TagRelation.RELATION_SPECIES;
		}else if("关联".equals(type)){
			return TagRelation.RELATION_RELEVANCE;
		}else if("限制".equals(type)){
			return TagRelation.RELATION_LIMIT;
		}
		return 0;
	}

	public List<Tag> likeTag(String likename) throws ParameterException {
		if(likename==null){
			throw new ParameterException();
		}
		return getGeneSqlDao().likeTag(likename);
	}

	public List<String> notAddAlias(String tagName,Set<String> alias) {
		List<String> list = new ArrayList<String>();
		Tag source =  getGeneDao().getTag(tagName);
		Tag tag = null;
		alias.remove("");
		for(String s : alias){
			s = s.trim();
			tag = getGeneDao().getTag(s);
			if(tag!=null&&source!=null&&!source.getAlias().contains(s)){
				list.add(s);
			}
		}
		return list;
	}

	public void removeTag(Tag tag) throws ParameterException {
		if(tag==null){
			throw new ParameterException();
		}
		getGeneSqlDao().removeTag(tag);
		getSubscribeTagDao().removeSubscribeTag(SubscribeTag.createCommendTag(tag));
		getGeneDao().removeTag(tag);
	}
	
	@Override
	public void removeTagChild(Tag tag) throws ParameterException {
		if(tag==null){
			throw new ParameterException();
		}
		Set<TagRelation> childSet = tag.getRelationSet();
		Tag t;
		for(TagRelation tr : childSet){
			t = getTag(tr.getTagName());
			if(t!=null){
				removeTag(t);
				System.out.println(t.getName());
				//getGeneDao().removeTag(t);
			}else{
				getGeneDao().removeRelation(tr);
				getGeneSqlDao().removeTagRelation(tr);
			}
		}
		getGeneDao().removeAllChildRelation(tag);
	}

	public void removeAll() {
		getGeneSqlDao().removeAllTag();
		getGeneDao().removeAll();
		getSubscribeTagDao().removeAll();
	}

	public void saveRelationSetting(Map<String, Double> map) throws ParameterException {
		if(map==null){
			throw new ParameterException();
		}
		getGeneSettingDao().saveSetting(map);
		getPicGeneDao().saveRelationRate(map);
	}

	public Map<String, Double> getMapRelationSetting() {
		return getPicGeneDao().findRelationRateMap();
	}

	public List<Tag> findTag(String name, Pagination pager)
			throws ParameterException {
		if(pager==null){
			throw new ParameterException();
		}
		return getGeneSqlDao().findTag(name, pager);
	}

	public void saveWeighSetting(Map<String, Double> map)
			throws ParameterException {
		if(map==null){
			throw new ParameterException();
		}
		getGeneSettingDao().saveSetting(map);
		getUserGeneDao().saveWeighRate(map);
		
	}

	public Map<String, Double> getMapWeighSetting() {
		return getUserGeneDao().findWeighRateMap();
	}

	public void issueCommendTag() {
		List<String> list = null;
		//addr
		list = convTagList(getGeneSqlDao().findCommendTag(Tag.TAGMODEL_ADDR));
		getGeneDao().saveCommendTag(list, Tag.TAGMODEL_ADDR);
		//来源
		list = convTagList(getGeneSqlDao().findCommendTag(Tag.TAGMODEL_ORIGIN));
		getGeneDao().saveCommendTag(list, Tag.TAGMODEL_ORIGIN);
		//tag
		list = convTagList(getGeneSqlDao().findCommendTag(Tag.TAGMODEL_TAG));
		getGeneDao().saveCommendTag(list, Tag.TAGMODEL_TAG);
	}
	
	private List<String> convTagList(List<Tag> tagList){
		List<String> list = new ArrayList<String>();;
		for(Tag tag : tagList){
			list.add(tag.getName());
		}
		return list;
	}
	
	private boolean isArtTag(String tagName){
		if(tagName==null){
			return false;
		}else if(tagName.startsWith("site:")){
			return false;
		}else{
			return true;
		}
	}

	public void saveSubscribeTags(String parentName,List<SelectedTag> tags) throws ParameterException {
		if(tags!=null&&tags.size()>0){
			Tag tag = null;
			SubscribeTag ct;
			TagCatalog tc = this.getTagCatalogDao().findTagCatalogForTagName(parentName);
			Long typeId = 0L;
			if(tc!=null){
				typeId = tc.getId();
			}
			for(SelectedTag t : tags){
				tag = this.getTag(t.getTagName());
				if(tag!=null){
					ct = this.getSubscribeTagDao().findSubscribeTag(tag.getName());
					if(ct==null){
						ct = SubscribeTag.createCommendTag(tag);
					}
					ct.setTypeId(typeId);
					if(t.isSelected()){
						tag.setTagSelected(Tag.SELECTED_TRUE);
						this.getSubscribeTagDao().saveOrUpdateSubscribeTag(ct);
						this.getUserGeneDao().saveSubscribeTag(ct);
						this.getGeneSqlDao().saveCommendTagAlias(tag);
					}else{
						tag.setTagSelected(Tag.SELECTED_FALSE);
						this.getSubscribeTagDao().removeSubscribeTag(ct);
						this.getUserGeneDao().removeSubscribeTag(ct.getTagName());
						this.getGeneSqlDao().saveCancelCommendTagAlias(tag);
					}
					
   					this.saveTag(tag,true,false);
				}
			}
		}
	}
	
	@Override
	public void removeSubscribeTag(String name) throws ParameterException {
		if(name==null){
			throw new ParameterException();
		}
		Tag tag = this.getTag(name);
		if(tag!=null){
			tag.setTagSelected(Tag.SELECTED_FALSE);
			this.saveTag(tag,true,false);
		}
		SubscribeTag ct = this.getSubscribeTagDao().findSubscribeTag(tag.getName());
		if(ct!=null){
			this.getSubscribeTagDao().removeSubscribeTag(ct);
			this.getUserGeneDao().removeSubscribeTag(ct.getTagName());
		}
		this.getGeneSqlDao().saveCancelCommendTagAlias(tag);
	}

	
	public void saveSubscribeTag(String tagName,boolean select,boolean special,int type,String displayName,Long typeId,File iconFile,File coverFile) throws ParameterException {
		if(tagName==null){
			throw new ParameterException();
		}
		Tag tag = this.getTag(tagName);
		if(tag!=null){
			SubscribeTag ct = this.getSubscribeTagDao().findSubscribeTag(tag.getName());
			if(ct!=null){
				ct.setDisplayType(type);
				if(displayName!=null){
					SubscribeTag dn = this.getSubscribeTagDao().findDisplayName(displayName);
					if(dn!=null){
						if(!dn.getTagName().equals(tagName)){
							throw new ParameterException("显示名称重复");
						}
						
					}
					ct.setDisplayName(displayName);
					if(select){
						tag.setCommend(Tag.COMMEND_TRUE);
						ct.setCommend(Tag.COMMEND_TRUE);
					}else{
						tag.setCommend(Tag.COMMEND_FALSE);
						ct.setCommend(Tag.COMMEND_FALSE);
					}
					if(special){
						ct.setSpecial(ct.SPECIAL_YES);
					}else{
						ct.setSpecial(ct.SPECIAL_NO);
					}
					if(typeId!=null){
						ct.setTypeId(typeId);
					}else{
						ct.setTypeId(0L);
					}

					this.getGeneSqlDao().saveCommendTagAlias(tag);
					this.getSubscribeTagDao().saveOrUpdateSubscribeTag(ct);
					this.getUserGeneDao().saveSubscribeTag(ct);
					this.saveTag(tag,true,false);

				}else{
					throw new ParameterException("显示名称为空");
				}

			}
		}
	}
	
	@Override
	public void saveCommend(String name, boolean select)  throws ParameterException {
		if(name==null){
			throw new ParameterException();
		}
		Tag tag = this.getTag(name);
		if(tag!=null){
			SubscribeTag ct = this.getSubscribeTagDao().findSubscribeTag(tag.getName());
			if(ct!=null){
				if(select){
					tag.setCommend(Tag.COMMEND_TRUE);
					ct.setCommend(Tag.COMMEND_TRUE);
				}else{
					tag.setCommend(Tag.COMMEND_FALSE);
					ct.setCommend(Tag.COMMEND_FALSE);
				}
				this.getSubscribeTagDao().saveOrUpdateSubscribeTag(ct);
				this.getUserGeneDao().saveSubscribeTag(ct);
				this.saveTag(tag,true,false);
			}
		}

	}

	
	public List<SubscribeTag> findSubscribeTag(String name,int tagModel,Pagination pager) {
		return this.getSubscribeTagDao().findSubscribeTagList(name,tagModel,pager);
	}

	public List<Tag> findTagList(String name,Pagination pager) {
		return getGeneSqlDao().findTagList(name,pager);
	}

	public List<SubscribeTag> findSubscribeTag(Long typeId, String name,
			int tagModel, Pagination pager) {
		return  this.getSubscribeTagDao().findSubscribeTagList(typeId,name,tagModel,pager);
	}

	@Override
	public List<TagCatalog> findTagCatalogList(int tagModel, Pagination pager) {
		return this.getTagCatalogDao().findTagCatalogList(tagModel, pager);
	}
	

	public void saveTagCatalog(TagCatalog tc) throws ParameterException{
		TagCatalog tagCatalog = this.getTagCatalogDao().findTagCatalog(tc.getName(),tc.getTagModel());
		if(tagCatalog!=null){
			if(tc.getId()==null||tagCatalog.getId().longValue()!=tc.getId()){
				throw new ParameterException("目录名重复");
			}
		}
		Tag tag = this.getTag(tc.getTagName());
		if(tag==null){
			throw new ParameterException("绑定的标签不存在");
		}
		TagCatalog tagCatalog1 = this.getTagCatalogDao().findTagCatalogForTagName(tc.getTagName());
		if(tagCatalog1!=null){
			if(tc.getId()==null||tagCatalog1.getId().longValue()!=tc.getId()){
				throw new ParameterException("重复绑定标签");
			}
		}
		if(tc.getId()==null){
			this.getTagCatalogDao().save(tc);
		}else{
			this.getTagCatalogDao().mergeTagCatalog(tc);
		}
		
	}

	@Override
	public void removeTagCatalog(TagCatalog tc) {
		this.getSubscribeTagDao().settingTypeNameNull(tc.getId());
		this.getTagCatalogDao().delete(tc);
	}

	@Override
	public TagCatalog findTagCatalog(String name,int model) {
		if(name!=null){
			return this.getTagCatalogDao().findTagCatalog(name,model);
		}
		return null;
	}

	@Override
	public TagCatalog findTagCatalog(Long id) {
		return this.getTagCatalogDao().findTagCatalog(id);
	}

	@Override
	public List<SubscribeTag> findCommendList(int tagModel, long catalogId) {
		Pagination pager = new Pagination();
		pager.setPageSize(300);
		List<SubscribeTag> list = getSubscribeTagDao().findCommendList(tagModel,catalogId);
		return list;
	}

	@Override
	public void saveCommendSort(String tagName, int sort) {
		getSubscribeTagDao().updateSort(tagName, sort);
	}

	@Override
	public List<TagCatalog> findCommendCatalogList(int tagModel, Pagination pager) {
		return this.getTagCatalogDao().findCommendCatalogList(tagModel, pager);
	}

	@Override
	public void saveCommendCatalogSort(long id, int sort) {
		this.getTagCatalogDao().saveCommendSort(id, sort);
	}

	@Override
	public void saveGradeRedis(Long artId, List<GeneGrade> list) {
		getPicGeneDao().savePicGeneGrade(artId, list);
	}
	
	
	public void tagSqlToRedis(){
		this.getGeneDao().removeAll();
		Pagination pager = new Pagination();
		pager.setPageSize(100);
		//pager.setPageNo(61);
		List<Tag> list = this.getGeneSqlDao().findTagList(pager);
		int count = 0;
		if(list!=null){
			Tag tag = null;
			Set<String> alias;
			while(list!=null&&list.size()>0&&pager.getTotalCount()>count){
				for(Tag t : list){
						tag = this.getGeneSqlDao().findTag(t.getName());
						if(tag!=null){
							count++;
							System.out.println(count+" "+t.getName());
							getGeneDao().saveTag(tag);
							
						}
					}

				pager.setPageNo(pager.getPageNo()+1);
				list = this.getGeneSqlDao().findTagList(pager);
			}
		}

	}
	

	@Override
	public Tag findSqlTag(String name) {
		return this.getGeneSqlDao().findTag(name);
	}

	@Override
	public void cacheAllSubscribeTag() {
		this.getUserGeneDao().removeSubscribeTagAll();
		List<SubscribeTag> list = this.getSubscribeTagDao().findAllSubscribeTag();
		if(list!=null){
			for(SubscribeTag st : list){
				this.getUserGeneDao().saveSubscribeTag(st);
			}
		}
	}


}
