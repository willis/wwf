package cn.com.icore.dictionary.service;

import java.util.List;

import cn.com.icore.dictionary.model.Dictionary;

public interface DictionaryService {
	public static final String ID_NAME = "dictionaryService";

	public void addDictionary(Dictionary paramDictionary);

	public void updateDictionary(Dictionary paramDictionary);

	public void saveDictionary(Dictionary paramDictionary);

	public Dictionary getDictionary(long paramLong);

	public boolean delDictionary(long paramLong);

	public List<Dictionary> getTree(long paramLong);

	public List<Dictionary> getDictionarysByParentId(long paramLong);
}
