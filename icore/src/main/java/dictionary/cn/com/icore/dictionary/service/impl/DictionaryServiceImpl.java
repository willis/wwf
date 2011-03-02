package cn.com.icore.dictionary.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cn.com.icore.dictionary.model.Dictionary;
import cn.com.icore.dictionary.service.DictionaryService;
import cn.com.icore.util.dao.CommonDao;

@SuppressWarnings("unchecked")
public class DictionaryServiceImpl extends CommonDao implements
		DictionaryService {

	public void addDictionary(Dictionary bean) {
		bean.setCurDate(new Date());
		super.add(bean);
	}

	public void updateDictionary(Dictionary bean) {
		bean.setCurDate(new Date());
		super.update(bean);
	}

	public Dictionary getDictionary(long id) {
		return (Dictionary) super.get(Dictionary.class, Long.valueOf(id));
	}

	public boolean delDictionary(long id) {
		boolean result;
		try{
		  super.remove(Dictionary.class, Long.valueOf(id));
		  result = true;
		}catch(Exception e){
			result = false;
			
		}
		return result;
	}

	public List<Dictionary> getTree(long rootId) {
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
