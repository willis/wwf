package cn.vivame.v2.gene.action;

import static java.util.Collections.EMPTY_LIST;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONSerializer;
import cn.vivame.v2.gene.model.SelectedTag;
import cn.vivame.v2.gene.model.SubscribeTag;
import cn.vivame.v2.gene.model.Tag;
import cn.vivame.v2.gene.model.TagCatalog;
import cn.vivame.v2.gene.model.TagRelation;

import com.mpaike.core.exception.ParameterException;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.util.HtmlUtil;
import com.mpaike.util.ParamHelper;
import com.mpaike.util.app.BaseAction;

public class GeneAction extends BaseAction {
	
	private static final String TEMPLATE_TREE= "{name:\"%1$s\",namecode:\"%2$s\",iconSkin:\"%3$s\",type:%4$s,semProperty:%5$s,alias:\"%6$s\",parentRelation:%7$s,tagSelected:%8$s,commend:%9$s,tagModel:%10$s,isParent:true}";
	private static final String TEMPLATE_TAG = "{\"name\":\"%1$s\",\"hasTag\":%2$s}";
	private static Map<String,Object> likeTagMap = new HashMap<String,Object>();
	
	private String operation;
	private boolean root;
	private String parentName;
	private String name;
	private String namecode;
	private String tags;
	private String iconSkin;

	private String ajaxContent;
	private Tag tag;
	private TagRelation tagRelation;
	private int status;
	private String userName;
	private String createDate;
	private String query;
	private int tagsave;
	private String[] subscribeTags;
	private long[] catalogs;
	private List<Tag> tagList;
	private List<SubscribeTag> subscribeTagList;
	private List<TagCatalog> tagcatalogList;
	private boolean opType;
	private String displayName;
	private Long typeId;
	private TagCatalog tagcatalog;
	private boolean special;
	private String errorMsg;
	private Map<String,Double> relationCountMap = new HashMap<String,Double>();
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String addView(){

		return "gene_add";
	}
	
	public String importRelationView(){
		return "gene_import_relation";
	}
	
	public void importRelation() throws ParameterException{
		List list = EMPTY_LIST;
//		String object_id = ParamHelper.getStr(request, "object_id", null);
//		if(StringUtils.isNotBlank(object_id)){
//			
//			List<Annex> annexList =  getAnnexService().findByObject_id(object_id,ANNEXTPYE_RELATION,SYSTEM_CMS);
//
//			String filepath;
//			for(Annex annex : annexList){
//				filepath = getBaseDir()+annex.getFilePath();
//				try {
//					list = getGeneService().saveRelationForFile(filepath);
//				} catch (FormatErrorException e) {
//					printSuccessJson("文件格式错误");
//				}
//			}
//			printJson(list.toString());
//
//		}else{
//			printSuccessJson("请添加上传");
//		}
	}
	
	public String search(){
		if(namecode==null || "undefined".equals(namecode)){
			name = Tag.TAG_ROOT;
		}else{
			name = decode(namecode);
		}
		try {
			tag = getGeneService().getTag(name);
		} catch (ParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "gene_search";
	}
	
	public void searchTag(){
		if(name==null){
			name = Tag.TAG_ROOT;
		}
		try {
			tag = getGeneService().getTag(name);
		} catch (ParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(tag==null){
			printBeanToJson("{'hasTag':false,'tag':'"+HtmlUtil.addSlashes(name)+"'}");
		}else{
			printBeanToJson("{'hasTag':true,'tag':"+String.format(TEMPLATE_TREE, addSlashes(tag.getName()), encode(tag.getName()), tag.getTypeIcon(), tag.getType(), tag.getSemProperty(), convArray(tag.getAlias()), "1", tag.getTagSelected(), tag.getCommend(), tag.getTagModel())+"}");
		}
	}
	
	public void likeTag() throws ParameterException{
		String json = (String)likeTagMap.get(query);
		if(json==null){
			json = tagsToJson(getGeneService().likeTag(query));
			likeTagMap.put(query+"_timezone", System.currentTimeMillis());
			likeTagMap.put(query, json);
		}else{
			long timezone = (Long)likeTagMap.get(query+"_timezone");
			if(System.currentTimeMillis()-timezone>600000){
				json = tagsToJson(getGeneService().likeTag(query));
				likeTagMap.put(query+"_timezone", System.currentTimeMillis());
				likeTagMap.put(query, json);
			}
		}
		printBeanToJson(json);
	}
	
	public void findTag(){
		if(name==null){
			name = Tag.TAG_ROOT;
		}
		try {
			tag = getGeneService().getTag(name);
			if(tag!=null){
				if(parentName!=null&&!"".equals(parentName)){
					TagRelation relation = getGeneService().findTagRelation(parentName,tag.getName());
					if(relation==null){
						printBeanToJson("["+tag.toString()+"]");
					}else{
						printBeanToJson("["+tag.toString()+","+getGeneService().findTagRelation(parentName,name).toString()+"]");
					}
				}else{
					printBeanToJson("["+tag.toString()+"]");
				}
			}else{
				printBeanToJson("[]");
			}
		} catch (ParameterException e) {
			printBeanToJson("[]");
		}

	}
	
	public String formTag(){
		return "gene_form_tag";
	}
	
	//删除所有数据，危险注释
	public void restData(){
		//this.getGeneService().removeAll();
		printBeanToJson("[]");
	}
	
	public String ajaxAddTags(){
		if(tags!=null){
			Tag t;
			String tagName;
			tags = tags.replaceAll("；", ";");
			String[] tagsStrArray = tags.split(";");
			if(tagsStrArray.length>0){
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				try {
					for(int i=0,n=tagsStrArray.length;i<n;i++){
						sb.append(",");
						tagName = tagsStrArray[i].trim();
						t = getGeneService().getTag(tagName);
						if(t!=null){
							if(t.getType()==Tag.TYPE_INSTANCE){
								sb.append(String.format(TEMPLATE_TAG,addSlashes(tagName),"true"));
							}else{
								sb.append(String.format(TEMPLATE_TAG,addSlashes(tagName),"false"));
							}
						}else{
							sb.append(String.format(TEMPLATE_TAG,addSlashes(tagName),"false"));
						}
					}
				} catch (ParameterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sb.append("]");
				if(sb.length()!=2){
					sb.deleteCharAt(1);
				}
				ajaxContent = sb.toString();
			}
		}
		if(ajaxContent==null){
			ajaxContent = "[]";
		}
		return "ajax_tree";
	}
	
	/**
	 * 保存tag
	 * @throws ParameterException 
	 */
	public void saveTag() throws ParameterException{
		
		if(tagsave==1){
			if(tag.getName()!=null){
				if("".equals(tag.getName().trim())||tag.getName().indexOf(";")!=-1||tag.getName().indexOf("；")!=-1||tag.getName().indexOf("%")!=-1){
					printSuccessJson("标签为空或者标签带有非法字符(  ;  ； %");
					return;
				}
				List list = getGeneService().notAddAlias(tag.getName(),tag.getAlias());
				if(list.size()>0){
					printSuccessJson("操作失败,"+JSONSerializer.toJSON(list)+"是已有标签，不能作为别名");
					return;
				}
				Tag t = getGeneService().getTag(tag.getName());
				if(t!=null){
					if(tag.getType()!=t.getType()&&((t.getType()==t.TYPE_INSTANCE_CONTAINER||t.getType()==t.TYPE_CONTAINER)||(tag.getType()==t.TYPE_INSTANCE_CONTAINER||tag.getType()==t.TYPE_CONTAINER))){
						printSuccessJson("标签类型是容器不能修改");
						return;
					}
				}
				this.getGeneService().saveTag(tag,false,true);
				printSuccessJson("操作成功");
			}else{
				printSuccessJson("操作失败");
			}
			return;
		}
		
		if(tag.getName()!=null){
			if("".equals(tag.getName().trim())||tag.getName().indexOf(";")!=-1||tag.getName().indexOf("；")!=-1||tag.getName().indexOf("%")!=-1){
				printSuccessJson("标签为空或者标签带有非法字符(  ;  ； %)");
				return;
			}
			tagRelation.setTagName(tag.getName());
			if(tagRelation.getParentTagName().trim().equalsIgnoreCase(tagRelation.getTagName().trim())){
				printSuccessJson("标签不能自我关联");
				return;
			}
			try {
				Tag t = getGeneService().getTag(tag.getName());
				if(t!=null){
					if(tag.getType()!=t.getType()&&((t.getType()==t.TYPE_INSTANCE_CONTAINER||t.getType()==t.TYPE_CONTAINER)||(tag.getType()==t.TYPE_INSTANCE_CONTAINER||tag.getType()==t.TYPE_CONTAINER))){
						printSuccessJson("标签类型是容器不能修改");
						return;
					}
					if(t.getParentRelationSet().contains(tagRelation)){
						//System.out.println(true);
					}else{
						if(getGeneService().hasName(tagRelation.getParentTagName(),tag.getName())){
							printSuccessJson("标签不能递归关联");
							return;
						}
					}
				}
				tag.getAlias().remove(""); 
				List list = getGeneService().notAddAlias(tag.getName(),tag.getAlias());
				if(list.size()>0){
					printSuccessJson("操作失败,"+JSONSerializer.toJSON(list)+"是已有标签，不能作为别名");
					return;
				}
				this.getGeneService().saveTagAndRelation(tag, false,tagRelation);
			} catch (ParameterException e) {
			
				printSuccessJson("操作失败");
				e.printStackTrace();
			}
			
			printSuccessJson("操作成功");
		}else{
			/**
			 * 导入标签文件
			 */
			try{
				String object_id = ParamHelper.getStr(request, "object_id", null);
//				if(StringUtils.isNotBlank(object_id)){
//					
//					List<Annex> annexList =  getAnnexService().findByObject_id(object_id,ANNEXTPYE_TAG,SYSTEM_CMS);
//
//					String filepath;
//					if(annexList!=null&&!annexList.isEmpty()){
//						for(Annex annex : annexList){
//							filepath = getBaseDir()+annex.getFilePath();
//							this.getGeneService().saveTagForFile(filepath, tag.getType(), tag.getSemProperty(), tag.getTagSelected(), tag.getCommend(), tagRelation.getParentTagName(),tagRelation.getRelationType());
//						}
//						printSuccessJson("导入成功");
//					}else{
//						printSuccessJson("请添加上传");
//					}
//
//				}else{
//					printSuccessJson("请添加上传");
//				}
//			}catch(FormatErrorException ex){
//				printSuccessJson("文件格式错误");
			}catch(Exception ex){
				printSuccessJson("导入失败，文件中有非法字符");
			}
		}

	}
	
	/**
	 * ajax删除tag
	 * @return
	 */
	public String ajaxRemove(){
		try {
			getGeneService().remove(tagRelation);
		} catch (ParameterException e) {
			ajaxContent = "false";
			e.printStackTrace();
		}
		ajaxContent="true";
		return "ajax_tree";
	}
	
	/**
	 * 正向树
	 * @return
	 */
	public String tree(){
		return "gene_tree";
	}
	
	public String ajaxTree(){		
		if(namecode==null || "undefined".equals(namecode)){
			name = Tag.TAG_ROOT;
		}else{
			name = decode(namecode);
		}
		if(name!=null){
			Set<TagRelation> treeSet = null;
			try {
				treeSet = getGeneService().getChildTagRelation(name);
				ajaxContent = formatTree(name,treeSet,false);
			} catch (ParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "ajax_tree";
	}

	/**
	 * 反向树
	 * @return
	 */
	public String reverseTree(){
		if(name!=null&&!"undefined".equals(name)){
			name = this.decode(name);
			if(name!=null){
				try {
					tag = getGeneService().getTag(name);
				} catch (ParameterException e) {
					e.printStackTrace();
				}
			}
		}else{
			name = null;
		}
		return "gene_reversetree";
	}
	public String ajaxReverseTree(){
		if(namecode==null || "undefined".equals(namecode)){
			name = Tag.TAG_ROOT;
		}else{
			name = decode(namecode);
		}
		if(name!=null){
			Set<TagRelation> treeSet = null;
			try {
				treeSet = getGeneService().getParentTagRelation(name);
				ajaxContent = formatTree(name,treeSet,true);
			} catch (ParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println(ajaxContent);
		return "ajax_tree";
	}
	
	//================= private ================
	private String formatTree(String name,Collection<TagRelation> treeSet,boolean isParent) throws ParameterException{
		StringBuilder sb = new StringBuilder();
		Iterator iterator = treeSet.iterator();
		TagRelation tagRelation = null;
		Tag tempTag;
		sb.append("[");
		if(isParent){
			while(iterator.hasNext()){
				tagRelation = (TagRelation)iterator.next();
				tempTag = getGeneService().getTag(tagRelation.getParentTagName());
				if(tempTag!=null){
					sb.append(",").append(String.format(TEMPLATE_TREE,addSlashes(tagRelation.getParentTagName()), encode(tagRelation.getParentTagName()), tempTag.getTypeIcon(), tempTag.getType(),tempTag.getSemProperty(), convArray(tempTag.getAlias()), tagRelation.getRelationType(), tempTag.getTagSelected(), tempTag.getCommend(), tempTag.getTagModel()));
				}else{
					getGeneService().remove(tagRelation);
				}
			}
		}else{
			while(iterator.hasNext()){
				tagRelation = (TagRelation)iterator.next();
				//System.out.println(tagRelation);
				tempTag = getGeneService().getTag(tagRelation.getTagName());
				if(tempTag!=null){
					sb.append(",").append(String.format(TEMPLATE_TREE,addSlashes(tagRelation.getTagName()), encode(tagRelation.getTagName()), tempTag.getTypeIcon(), tempTag.getType(), tempTag.getSemProperty(), convArray(tempTag.getAlias()), tagRelation.getRelationType(), tempTag.getTagSelected(), tempTag.getCommend(), tempTag.getTagModel()));
				}else{
					getGeneService().remove(tagRelation);
				}
			}
		}
		
		sb.append("]");
		if(sb.length()!=2){
			sb.deleteCharAt(1);
		}
		//System.out.println(sb.toString());
		return sb.toString();
	}//end
	
	public String tagsToJson(List<Tag> tagList){
		boolean frist = true;
		StringBuilder sb = new StringBuilder();
		sb.append("{query:'").append(query).append("',suggestions:");
		sb.append("[");
		for(Tag t : tagList){
			if(frist){
				frist = false;
			}else{
				sb.append(",");
			}
			sb.append("'").append(HtmlUtil.addSlashes(t.getName())).append("'");
		}
		sb.append("],data:['").append(query).append("']}");
		return sb.toString();
	}
	
	
	public int hashKey(String name){
		return name.hashCode();
	}
	
	private static String convArray(Set<String> alias){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(String a : alias){
			sb.append(",").append(HtmlUtil.addSlashes(a));
		}
		sb.append("]");
		if(sb.length()!=2){
			sb.deleteCharAt(1);
		}
		return sb.toString();
	}

	public String settingView(){
		relationCountMap = this.getGeneService().getMapRelationSetting();
		return "gene_setting";
	}
	
	public String settingWeighView(){
		relationCountMap = this.getGeneService().getMapWeighSetting();
		return "gene_weigh_setting";
	}
	
	public void setting(){
		try {
			if(status==1){
				getGeneService().saveRelationSetting(relationCountMap);
			}else if(status ==2){
				getGeneService().saveWeighSetting(relationCountMap);
			}
		} catch (ParameterException e) {
			printSuccessJson("操作失败");
			e.printStackTrace();
		}
		printSuccessJson("操作成功");
	}
	
	public String subscribeView(){
		return "gene_subscribe";
	}
	
	public String ajaxSubscribeTags(){
		return ajaxTree();
	}
	
	public void subscribeSave(){
		try {
			getGeneService().saveSubscribeTags(parentName,convSelectedTag(subscribeTags));
		} catch (ParameterException e) {
			printSuccessJson("操作失败，参数错误");
		}
		printSuccessJson("操作成功");
	}
	
	private static List<SelectedTag> convSelectedTag(String[] tags){
		List<SelectedTag> stList = new ArrayList<SelectedTag>();
		if(tags!=null){
			SelectedTag st = null;
			String[] tarr;
			for(String t : tags){
				if(!"".equals(t)){
					tarr = t.split(";");
					st = new SelectedTag();
					st.setTagName(tarr[0]);
					st.setSelected(Boolean.valueOf(tarr[1]));
					stList.add(st);
				}
			}
		}
		return stList;
	}

	//订阅和推荐
	public String subscribeList(){
		subscribeTagList = this.getGeneService().findSubscribeTag(name,status, pageInfo);
		return "subscribe_list";
	}
	

	public String siteSubscribeList() throws ParameterException{
		if(typeId==null){
			typeId=-1L;
		}
		subscribeTagList = this.getGeneService().findSubscribeTag(typeId,name,status, pageInfo);
		Pagination p1 = new Pagination();
		p1.setPageSize(30);
		tagcatalogList = this.getGeneService().findTagCatalogList(status, p1 );
		request.setAttribute("myPage", pageInfo);
		return "sitesubscribe_list";
	}
	
	public String tagCatalogView(){
		if(tagcatalog!=null&&tagcatalog.getId()!=null){
			tagcatalog = this.getGeneService().findTagCatalog(tagcatalog.getId());
		}
		return "tagcatalog_add";
	}
	
	public String saveTagCatalog(){
		tagsave=1;
		try {
			this.getGeneService().saveTagCatalog(tagcatalog);
		} catch (ParameterException e) {
			errorMsg = e.getMessage();
			return "tagcatalog_add";
		}
		return "tagcatalog_save";
	}
	
	public void removeTagCatalog(){
		this.getGeneService().removeTagCatalog(tagcatalog);
		printSuccessJson("操作成功");
	}
	
	
	
	public String tagCatalogList(){
		tagcatalogList = this.getGeneService().findTagCatalogList(status, pageInfo);
		return "tagcatalog_list";
	}
	
	public void commendSave() {
		try{
			if(namecode==null || "undefined".equals(namecode)){
			
			}else{
				name = decode(namecode);
				this.getGeneService().saveSubscribeTag(name, opType, special, status,displayName,typeId,null,null);
			}
			printSuccessJson("操作成功");
		}catch(ParameterException pe){
			printSuccessJson(pe.getMessage());
		}
	}
	
	public void ajaxCommend() throws ParameterException {
		if(namecode==null || "undefined".equals(namecode)){
		
		}else{
			name = decode(namecode);
			this.getGeneService().saveCommend(name, opType);
		}
		printSuccessJson("操作成功");
	}
	
	public void commendRemove() throws ParameterException{
		if(namecode==null || "undefined".equals(namecode)){
		
		}else{
			name = decode(namecode);
			this.getGeneService().removeSubscribeTag(name);
		}
		printSuccessJson("操作成功");
	}
	
	public void removeSubscribeList() throws ParameterException{
		if(tags!=null&&!"undefined".equals(tags)){
			String[] list = tags.split(",");
			for(String name : list){
				this.getGeneService().removeSubscribeTag(decode(name));
			}
		}
		printSuccessJson("操作成功");
	}
	
	public String tagRemoveList(){
		tagList = this.getGeneService().findTagList(name,pageInfo);
		return "tag_remove_list";
	}
	
	public String removeTag() throws ParameterException{
		if(namecode==null || "undefined".equals(namecode)){
		
		}else{
			name = decode(namecode);
			tag = getGeneService().getTag(name);
			if(tag!=null){
				this.getGeneService().removeTag(tag);
			}
		}
		name="";
		return tagRemoveList();
	}
	
	public String commendList(){
		subscribeTagList = this.getGeneService().findCommendList(status, typeId);
		return "commend_list";
	}
	
	public void saveCommendSort(){
		if(subscribeTags!=null){
			int sortNum = subscribeTags.length;
			for(int i=0,n=subscribeTags.length;i<n;i++,sortNum--){
				name = decode(subscribeTags[i]);
				if(name!=null&&!"".equals(name)){
					this.getGeneService().saveCommendSort(name, sortNum);
				}
			}
		}
		printSuccessJson("操作成功");
	}
	
	public String commendTagCatalogList(){
		tagcatalogList = this.getGeneService().findCommendCatalogList(status, pageInfo);
		return "commend_tagcatalog_list";
	}
	
	public void saveCommendCatalogSort(){
		if(catalogs!=null){
			int sortNum = catalogs.length;
			for(int i=0,n=catalogs.length;i<n;i++,sortNum--){
				this.getGeneService().saveCommendCatalogSort(catalogs[i], sortNum);
			}
		}
		printSuccessJson("操作成功");
	}
	
	public void cacheSubscribeInit(){
		this.getGeneService().cacheAllSubscribeTag();
		printSuccessJson("操作成功");
	}
	
	
	//set,get ======================================
	
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAjaxContent() {
		return ajaxContent;
	}

	public void setAjaxContent(String ajaxContent) {
		this.ajaxContent = ajaxContent;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public TagRelation getTagRelation() {
		return tagRelation;
	}

	public void setTagRelation(TagRelation tagRelation) {
		this.tagRelation = tagRelation;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getTagsave() {
		return tagsave;
	}

	public void setTagsave(int tagsave) {
		this.tagsave = tagsave;
	}

	public String getNamecode() {
		return namecode;
	}

	public void setNamecode(String namecode) {
		this.namecode = namecode;
	}

	public Map<String, Double> getRelationCountMap() {
		return relationCountMap;
	}

	public void setRelationCountMap(Map<String, Double> relationCountMap) {
		this.relationCountMap = relationCountMap;
	}

	public String[] getSubscribeTags() {
		return subscribeTags;
	}

	public void setSubscribeTags(String[] subscribeTags) {
		this.subscribeTags = subscribeTags;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public boolean getOpType() {
		return opType;
	}

	public void setOpType(boolean opType) {
		this.opType = opType;
	}

	public List<SubscribeTag> getSubscribeTagList() {
		return subscribeTagList;
	}

	public void setSubscribeTagList(List<SubscribeTag> subscribeTagList) {
		this.subscribeTagList = subscribeTagList;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public List<TagCatalog> getTagcatalogList() {
		return tagcatalogList;
	}

	public void setTagcatalogList(List<TagCatalog> tagcatalogList) {
		this.tagcatalogList = tagcatalogList;
	}

	public TagCatalog getTagcatalog() {
		return tagcatalog;
	}

	public void setTagcatalog(TagCatalog tagcatalog) {
		this.tagcatalog = tagcatalog;
	}

	public boolean getSpecial() {
		return special;
	}

	public void setSpecial(boolean special) {
		this.special = special;
	}

	public long[] getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(long[] catalogs) {
		this.catalogs = catalogs;
	}



}
