package cn.com.icore.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cn.com.icore.user.model.SysMenu;
import cn.com.icore.user.service.SysMenuService;
import cn.com.icore.util.dao.hibernate.CommonDao;

@SuppressWarnings("unchecked")
public class SysMenuServiceImpl extends CommonDao implements SysMenuService {

	public void addMenu(SysMenu menu) {
		menu.setCurDate(new Date());
		super.add(menu);
	}

	public void updateMenu(SysMenu menu) {
		menu.setCurDate(new Date());
		super.update(menu);
	}

	public boolean delMenu(long id) {
		boolean result;
		try {
			super.remove(SysMenu.class, Long.valueOf(id));
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public SysMenu getMenu(long id) {
		return (SysMenu) super.get(SysMenu.class, Long.valueOf(id));
	}

	public List<SysMenu> getTree(long rootId) {
		SysMenu rootObj = (SysMenu) super.get(SysMenu.class, Long
				.valueOf(rootId));
		List tree = new ArrayList();

		loadTreeChilds(rootObj, tree, 1);
		return tree;
	}

	private void loadTreeChilds(SysMenu d, List<SysMenu> tree, int level) {
		tree.add(d);
		d.setLevel(Integer.valueOf(level));

		Iterator childs = d.getChilds().iterator();
		while (childs.hasNext()) {
			SysMenu child = (SysMenu) childs.next();
			loadTreeChilds(child, tree, level + 1);
		}
	}

	public List<SysMenu> getMenusByParentId(long parentId) {
		return super
				.find(" select new SysMenu(d.id,d.name,d.description,d.orderBy,d.img,d.link,d.alias) from SysMenu d where d.parentObj.id= "
						+ parentId + " order by d.orderby ");
	}

	public boolean save(SysMenu bean) {
		boolean result;
		try {
			super.save(bean);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
}
