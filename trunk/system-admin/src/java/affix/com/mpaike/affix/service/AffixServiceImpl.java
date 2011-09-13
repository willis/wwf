package com.mpaike.affix.service;

import java.util.List;

import com.mpaike.affix.model.Affix;
import com.mpaike.util.dao.CommonDao;

public class AffixServiceImpl extends CommonDao implements AffixService {
	
	 
	public void addAffix(Affix affix){
		affix.setHotNum(0l);
		super.add(affix);
	}
	public void delAffix(long id){
		super.remove(Affix.class,id);
	}
	
	public List<Affix> getAffixs(long objectType,String objectId){
		return super.find(" from Affix a where a.objectType= "+objectType+" and a.objectId=? order by a.id desc ",new Object[]{objectId});
	}
	public Affix getAffix(long id){
		return (Affix)super.get(Affix.class, id);
	}
	
	public void updateAffixId(long objectType,String oldObjId,String newObjId ){
		super.getHibernateTemplate().bulkUpdate(" update Affix a set a.objectId=? where a.objectType= "+objectType+" and a.objectId=? ",new Object[]{newObjId,oldObjId});
	}
	
	public void updateAffixHotAdd(long id ){
		 
		super.getHibernateTemplate().bulkUpdate(" update Affix a set a.hotNum=a.hotNum+1 where a.id="+id);
	}
	@Override
	public void deleteByTypeAndOBjId(Integer type, String objId) {
		
		super.getHibernateTemplate().bulkUpdate("delete from Affix where objectType=? and objectId=?",new Object[]{type,objId});
		
	}
	
	
	
}
