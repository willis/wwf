package cn.com.icore.util.hibernate.dao;

import com.fins.gt.model.PageInfo;
import com.fins.gt.model.SortInfo;
import com.fins.gt.server.GridServerHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class GtGridCommonDao extends CommonDao {

	public GridServerHandler list(GridServerHandler handler, Class c,
			String countString, String queryString, Object[] params) {
		if (Map.class.getName().equals(c.getName())) {
			List beans = listToMapRow(handler, countString, queryString, params);
			handler.setData(beans);
		} else {
			List beans = list(handler, countString, queryString, params);
		
			handler.setData(beans, c);
		}
		return handler;
	}
	
	

	public GridServerHandler listBySql(GridServerHandler handler, Class c,
			String countString, String queryString, Object[] params) {
		List beans = listBySql(handler, countString, queryString, params);
		handler.setData(beans);
		return handler;
	}

	public GridServerHandler listToMapRow(GridServerHandler handler, Class c,
			String countString, String queryString, Object[] params) {
		List beans = listToMapRow(handler, countString, queryString, params);
		handler.setData(beans);
		return handler;
	}

	public GridServerHandler list(GridServerHandler handler, Class c,
			String queryString, Object[] params) {
		return list(handler, c, queryString, queryString, params);
	}

	public GridServerHandler listToMapRow(GridServerHandler handler, Class c,
			String queryString, Object[] params) {
		return listToMapRow(handler, c, queryString, queryString, params);
	}

	public GridServerHandler list(GridServerHandler handler, Class c,
			String queryString) {
		return list(handler, c, queryString, queryString, new Object[0]);
	}

	public GridServerHandler listToMapRow(GridServerHandler handler, Class c,
			String queryString) {
		return listToMapRow(handler, c, queryString, queryString, new Object[0]);
	}

	public GridServerHandler list(GridServerHandler handler, Class c,
			String countString, String queryString) {
		return list(handler, c, countString, queryString, new Object[0]);
	}

	public GridServerHandler listToMapRow(GridServerHandler handler, Class c,
			String countString, String queryString) {
		return listToMapRow(handler, c, countString, queryString, new Object[0]);
	}

	public List list(GridServerHandler handler, String countString,
			String queryString, Object[] params) {
		int totalRowNum = handler.getTotalRowNum();
		if (handler.getPageSize() > 0) {
			totalRowNum = super.count(countString, params).intValue();
			handler.setTotalRowNum(totalRowNum);

			if (totalRowNum == 0) {
				return new ArrayList();
			}

			int temp_i = handler.getTotalRowNum() % handler.getPageSize() == 0 ? 0
					: 1;
			handler.setTotalPageNum(handler.getTotalRowNum()
					/ handler.getPageSize() + temp_i);

			if (handler.getPageNum() > handler.getTotalPageNum()) {
				handler.setPageNum(handler.getTotalPageNum());
			}
			if (handler.getPageNum() < 1) {
				handler.setPageNum(1);
			}

		}

		StringBuffer sortCond = new StringBuffer();
		List<SortInfo>  sortInfos = handler.getSortInfo();

		if (sortInfos != null) {
			for (SortInfo sortInfo : sortInfos) {
				String fieldName = sortInfo.getFieldName();
				String sortOrder = sortInfo.getSortOrder();
				if ((sortOrder.equalsIgnoreCase("defaultsort"))
						|| ("op".equals(fieldName)))
					continue;
				if (sortCond.length() > 0)
					sortCond.append(",");
				sortCond.append(" " + fieldName + " " + sortOrder + " ");
			}

			if (sortCond.length() > 0) {
				queryString = queryString + " order by " + sortCond;
			}
		}

		List beans = super.find(queryString, params, handler.getPageSize(),
				handler.getPageNum());
		return beans;
	}

	public List listToMapRow(GridServerHandler handler, String countString,
			String queryString, Object[] params) {
		int totalRowNum = handler.getTotalRowNum();
		if (handler.getPageSize() > 0) {
			totalRowNum = super.count(countString, params).intValue();
			handler.setTotalRowNum(totalRowNum);

			if (totalRowNum == 0) {
				return new ArrayList();
			}

			int temp_i = handler.getTotalRowNum() % handler.getPageSize() == 0 ? 0
					: 1;
			handler.setTotalPageNum(handler.getTotalRowNum()
					/ handler.getPageSize() + temp_i);

			if (handler.getPageNum() > handler.getTotalPageNum()) {
				handler.setPageNum(handler.getTotalPageNum());
			}
			if (handler.getPageNum() < 1) {
				handler.setPageNum(1);
			}
		}

		StringBuffer sortCond = new StringBuffer();
		List<SortInfo> sortInfos = handler.getSortInfo();

		if (sortInfos != null) {
			for (SortInfo sortInfo : sortInfos) {
				String fieldName = sortInfo.getFieldName();
				String sortOrder = sortInfo.getSortOrder();
				if ((sortOrder.equalsIgnoreCase("defaultsort"))
						|| ("op".equals(fieldName)))
					continue;
				if (sortCond.length() > 0)
					sortCond.append(",");
				sortCond.append(" " + fieldName + " " + sortOrder + " ");
			}

			if (sortCond.length() > 0) {
				queryString = queryString + " order by " + sortCond;
			}
		}

		List beans = super.findToMapRow(queryString, params, handler
				.getPageSize(), handler.getPageNum());
		return beans;
	}

	public List<Map> listBySql(GridServerHandler handler, String countString,
			String queryString, Object[] params) {
		int totalRowNum = handler.getTotalRowNum();
		if (handler.getPageSize() > 0) {
			totalRowNum = super.count(countString, params).intValue();
			handler.setTotalRowNum(totalRowNum);

			if (totalRowNum == 0) {
				return new ArrayList();
			}
		}
		StringBuffer sortCond = new StringBuffer();
		List<SortInfo>  sortInfos = handler.getSortInfo();

		if (sortInfos != null) {
			for (SortInfo sortInfo : sortInfos) {
				String fieldName = sortInfo.getFieldName();
				String sortOrder = sortInfo.getSortOrder();
				if ((sortOrder.equalsIgnoreCase("defaultsort"))
						|| ("op".equals(fieldName)))
					continue;
				if (sortCond.length() > 0)
					sortCond.append(",");
				sortCond.append(" " + fieldName + " " + sortOrder + " ");
			}

			if (sortCond.length() > 0) {
				queryString = queryString + " order by " + sortCond;
			}
		}

		List beans = super.findBySql(queryString, params,
				handler.getPageSize(), handler.getPageNum());
		return beans;
	}

	public List list(GridServerHandler handler, String queryString) {
		return list(handler, queryString, queryString, new Object[0]);
	}

	public List listToMapRow(GridServerHandler handler, String queryString) {
		return listToMapRow(handler, queryString, queryString, new Object[0]);
	}

	public List list(GridServerHandler handler, String countString,
			String queryString) {
		return list(handler, countString, queryString, new Object[0]);
	}

	public List listToMapRow(GridServerHandler handler, String countString,
			String queryString) {
		return listToMapRow(handler, countString, queryString, new Object[0]);
	}

	public List list(GridServerHandler handler, String queryString,
			Object[] params) {
		return list(handler, queryString, queryString, params);
	}

	public List listToMapRow(GridServerHandler handler, String queryString,
			Object[] params) {
		return listToMapRow(handler, queryString, queryString, params);
	}




}
