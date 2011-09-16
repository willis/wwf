package com.mpaike.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.mpaike.sys.service.impl.BaseService;
import com.mpaike.user.model.SysMenu;
import com.mpaike.user.service.SysMenuService;


@SuppressWarnings("unchecked")
public class SysMenuServiceImpl extends BaseService implements SysMenuService {

	public void addMenu(SysMenu menu) {
		menu.setCurDate(new Date());
		this.getSysMenuDao().save(menu);
	}

	public void updateMenu(SysMenu menu) {
		menu.setCurDate(new Date());
		this.getSysMenuDao().update(menu);
	}

	public boolean delMenu(long id) {
		boolean result;
		try {
			this.getSysMenuDao().delete(id);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public SysMenu getMenu(long id) {
		return this.getSysMenuDao().get(id);
	}

	public List<SysMenu> getTree(long rootId) {
		SysMenu rootObj = this.getSysMenuDao().get(rootId);
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
		return this.getSysMenuDao().getMenusByParentId(parentId);
	}

	public boolean save(SysMenu bean) {
		boolean result;
		try {
			this.getSysMenuDao().saveOrUpdate(bean);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
}

