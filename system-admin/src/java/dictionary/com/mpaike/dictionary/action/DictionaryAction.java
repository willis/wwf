package com.mpaike.dictionary.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mpaike.dictionary.model.Dictionary;
import com.mpaike.dictionary.service.DictionaryService;
import com.mpaike.util.MyBeanUtils;
import com.mpaike.util.ParamHelper;
import com.mpaike.util.app.ApplictionContext;
import com.mpaike.util.app.BaseAction;


@SuppressWarnings("unchecked")
public class DictionaryAction extends BaseAction{

	DictionaryService getDictionaryService() {
		return (DictionaryService) ApplictionContext.getInstance()
		.getBean(DictionaryService.ID_NAME);
	}
	
	private List<Dictionary> tree = new ArrayList<Dictionary>();
	private Dictionary rootObj;
	private Dictionary dictionary;
	/**
	 * @author 陈海峰
	 * @createDate 2011-1-28 下午04:41:09
	 * @description 
	 */
	private static final long serialVersionUID = 1L;

	public String listDictionary(){
	    long rootId = ParamHelper.getLongParamter(request, "rootId", Long.parseLong(super.getAppProps().get("dictionaryRootId").toString()));
	    tree = getDictionaryService().getTree(rootId);
	    rootObj = tree.remove(0);
	    return "dictionaryList";
		
	}
	public String toAddDictionary(){
		long pid = ParamHelper.getLongParamter(request, "pid", -1L);
		rootObj = getDictionaryService().getDictionary(pid);
		return "toAddDictionary";
	}
	
	public String toEditDictionary(){
	
		long pid = ParamHelper.getLongParamter(request, "id", -1L);
		dictionary = getDictionaryService().getDictionary(pid);
		rootObj = dictionary.getParentObj();
		
		return "toEditDictionary";
		
	}
	public void save(){
		String result = "";
		if(dictionary.getId()==null){
			result ="添加成功!";
		}else{
			
			Dictionary target = getDictionaryService().getDictionary(dictionary.getId());
			
		    MyBeanUtils.fillForNotNullObject(target, dictionary);
		    dictionary = target;
			
			result ="修改成功!";
		}
		if(dictionary.getOrderby()==null){
			dictionary.setOrderby(0);
		}
		dictionary.setCurDate(new Date());
		getDictionaryService().saveDictionary(dictionary);
		super.printSuccessJson(response, result);
		
	}
	public void del(){
		String result = "";
		 long id = ParamHelper.getLongParamter(request, "id", -1L);
		 if (id == -1L)
			 result ="请选择要删除的数据!";
		 
		  if(getDictionaryService().delDictionary(id)){
			  result ="删除数据成功!";
		  }else{
			  result ="删除数据失败!";
		  }
		    super.printSuccessJson(response, result);
	}

	/**
	 * @return the tree
	 */
	public List<Dictionary> getTree() {
		return tree;
	}

	/**
	 * @param tree the tree to set
	 */
	public void setTree(List<Dictionary> tree) {
		this.tree = tree;
	}


	/**
	 * @return the rootObj
	 */
	public Dictionary getRootObj() {
		return rootObj;
	}


	/**
	 * @param rootObj the rootObj to set
	 */
	public void setRootObj(Dictionary rootObj) {
		this.rootObj = rootObj;
	}
	/**
	 * @return the dictionary
	 */
	public Dictionary getDictionary() {
		return dictionary;
	}
	/**
	 * @param dictionary the dictionary to set
	 */
	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

}
