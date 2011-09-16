package com.mpaike.affix.model.dao.impl;

import java.util.List;

import com.mpaike.affix.model.Affix;
import com.mpaike.affix.model.dao.IAffixDao;
import com.mpaike.core.database.hibernate.BaseDaoImpl;

public class AffixDao extends BaseDaoImpl<Affix> implements IAffixDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Affix> getAffixs(long objectType, String objectId) {
		
		return this.find(" from Affix a where a.objectType= "+objectType+" and a.objectId=? order by a.id desc ",new Object[]{objectId});
	}

	@Override
	public void updateAffixId(long objectType, String oldObjId, String newObjId) {
		this.updateAndDelBeanForSQL(" update Affix a set a.objectId=? where a.objectType= "+objectType+" and a.objectId=? ",new Object[]{newObjId,oldObjId});
		
	}

	@Override
	public void updateAffixHotAdd(long id) {
		this.updateAndDelBeanForSQL(" update Affix a set a.hotNum=a.hotNum+1 where a.id=?",new Object[]{id});
		
	}

	@Override
	public void deleteByTypeAndOBjId(Integer type, String objId) {
		this.updateAndDelBeanForSQL("delete from Affix where objectType=? and objectId=?",new Object[]{type,objId});
		
	}

}
