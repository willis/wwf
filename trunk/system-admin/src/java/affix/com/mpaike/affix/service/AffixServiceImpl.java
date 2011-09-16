package com.mpaike.affix.service;

import java.util.List;

import com.mpaike.affix.model.Affix;
import com.mpaike.sys.service.impl.BaseService;

public class AffixServiceImpl extends BaseService implements AffixService {
	
	 
	public void addAffix(Affix affix){
		affix.setHotNum(0l);
		this.getAffixDao().save(affix);
	}
	public void delAffix(long id){
		this.getAffixDao().deleteById(id);
	}
	
	public List<Affix> getAffixs(long objectType,String objectId){
		return this.getAffixDao().getAffixs(objectType, objectId);
	}
	public Affix getAffix(long id){
		return this.getAffixDao().get(id);
	}
	
	public void updateAffixId(long objectType,String oldObjId,String newObjId ){
		this.getAffixDao().updateAffixId(objectType, oldObjId, newObjId);
	}
	
	public void updateAffixHotAdd(long id ){
		this.getAffixDao().updateAffixHotAdd(id);
	}
	@Override
	public void deleteByTypeAndOBjId(Integer type, String objId) {
		
		this.getAffixDao().deleteByTypeAndOBjId(type, objId);
		
	}
	
	
	
}
