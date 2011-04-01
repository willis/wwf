package cn.com.icore.upload.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.com.icore.upload.model.Annex;
import cn.com.icore.upload.service.AnnexService;
import cn.com.icore.util.dao.hibernate.CommonDao;

import com.fins.gt.model.PageInfo;

public class AnnexServiceImpl extends CommonDao implements AnnexService {

	@SuppressWarnings("unchecked")
	public List<Annex> find(String object_id, String type, PageInfo page) {
		StringBuffer sql = new StringBuffer();
		List params = new ArrayList();
		if (StringUtils.isNotBlank(object_id)) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" a.object_id=?");
			params.add(object_id);
		}
		if (StringUtils.isNotBlank(type)) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" a.type=?");
			params.add(type);
		}
		if (sql.length() > 0)
			sql.insert(0, " where ");
		
		String select =  " Select new Annex(a.id,a.fileNames,a.fileSize,a.filePath,a.type,a.date,a.object_id) from Annex a ";
		if(params.size()>0)
			return super.find(" from Annex a "+ sql.toString(), select + sql.append(" order by id desc ")
				.toString(),params.toArray(), page);
		else
			return new ArrayList();
	}

	public Annex get(Long id) {

		return (Annex) super.get(Annex.class, id);
	}

	public void remove(Annex annex) {
		super.remove(annex);
	}

	public void save(Annex annex) {
		super.save(annex);
	}

	public int mycount(String object_id, String type) {

		StringBuffer stb = getHql(object_id, type);

		return super.count(stb.toString(), new Object[] { object_id, type });
	}

	public StringBuffer getHql(String object_id, String type) {

		StringBuffer stb = new StringBuffer("from Annex a where 1=1");
		if (StringUtils.isNotBlank(object_id)) {
			stb.append(" and a.object_id=?");
		}
		if (StringUtils.isNotBlank(type)) {
			stb.append(" and a.type=?");
		}
		return stb;
	}

	public void saveBatch(String object_id, String real_id) {

		this.getHibernateTemplate().bulkUpdate(
				" update Annex a set object_id=? where object_id = ?",
				new Object[] { real_id, object_id });

	}

	@SuppressWarnings("unchecked")
	public List<Annex> findByObject_id(String object_id) {

		StringBuffer stb = new StringBuffer(
				"from Annex a where a.object_id in('" + object_id + "')");
		return super.find(stb.toString());
	}

	@Override
	public void remove(Long id) {
		super.remove(Annex.class, id);
		
	}

}
