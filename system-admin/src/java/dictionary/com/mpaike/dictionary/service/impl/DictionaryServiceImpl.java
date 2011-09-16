package com.mpaike.dictionary.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.mpaike.core.database.hibernate.BaseDaoImpl;
import com.mpaike.dictionary.model.Dictionary;
import com.mpaike.dictionary.service.DictionaryService;


@SuppressWarnings("unchecked")
public class DictionaryServiceImpl extends BaseDaoImpl<Dictionary> implements
		DictionaryService {

	public void addDictionary(Dictionary bean) {
		bean.setCurDate(new Date());
		this.saveOrUpdate(bean);
	}

	public void updateDictionary(Dictionary bean) {
		bean.setCurDate(new Date());
		super.update(bean);
	}

	public Dictionary getDictionary(long id) {
		return (Dictionary) this.get(Long.valueOf(id));
	}

	public boolean delDictionary(long id) {
		boolean result;
		try{
		  this.deleteById(Long.valueOf(id));
		  result = true;
		}catch(Exception e){
			result = false;
			
		}
		return result;
	}

	public List<Dictionary> listTree(long rootId) {
		Dictionary rootObj = getDictionary(rootId);
		List tree = new ArrayList();
		loadTreeChilds(rootObj, tree);
		return tree;
	}

	private void loadTreeChilds(Dictionary d, List<Dictionary> tree) {
		tree.add(d);
		Iterator childs = d.getChilds().iterator();
		while (childs.hasNext()) {
			Dictionary child = (Dictionary) childs.next();
			loadTreeChilds(child, tree);
		}
	}

	public List<Dictionary> getDictionarysByParentId(long parentId) {
		return super
				.find(" select new Dictionary(d.id,d.name,d.describe,d.orderby,d.curDate,d.flag,d.defaultIndex) from Dictionary d where d.parentObj.id= "
						+ parentId + " order by d.orderby ");
	}

	public void saveDictionary(Dictionary paramDictionary) {

		super.save(paramDictionary);

	}
}
