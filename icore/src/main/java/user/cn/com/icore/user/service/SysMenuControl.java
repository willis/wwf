package cn.com.icore.user.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.com.icore.user.model.SysMenu;
import cn.com.icore.util.app.AppProps;
import cn.com.icore.util.app.ApplictionContext;

@SuppressWarnings("unchecked")
public class SysMenuControl {

	private List<SysMenu> tree = null;
	private static SysMenuControl me;

	public static synchronized SysMenuControl getInstance() {
		if (me == null)
			me = new SysMenuControl();
		return me;
	}

	public synchronized void putRootTree() {
		AppProps appProps = (AppProps) ApplictionContext.getInstance().getBean(
				"appProps");
		long rootId = Long.parseLong(appProps.get("sysMenuRootId").toString());
		SysMenuService service = (SysMenuService) ApplictionContext
				.getInstance().getBean("sysMenuService");

		this.tree = null;
		this.tree = service.getTree(rootId);

	}

	public SysMenu getSysMenuByAlias(String alias) {

		if ((alias == null) || (alias.trim().length() == 0))
			return null;
		if(this.tree==null)
			putRootTree();
		for (SysMenu menu : this.tree) {

			if (menu.getAlias() == null)
				continue;

			if (alias.trim().toLowerCase().equals(
					menu.getAlias().trim().toLowerCase())) {

				return menu;
			}
		}
		return null;
	}

	public List<SysMenu> getSysMenusByAlias(String alias,
			Set<SysMenu> userMenus, int levelLimit) {

		SysMenu d = getSysMenuByAlias(alias);
		List beans = new ArrayList();
		if (d == null)
			return beans;
		loadTreeChilds(d, beans, 0, levelLimit, userMenus);
		return beans;
	}

	public List<SysMenu> getSysMenuChildsByAlias(String alias,
			Set<SysMenu> userMenus) {
		SysMenu d = getSysMenuByAlias(alias);

		List beans = new ArrayList();
		if (d == null)
			return beans;
		for (SysMenu menu : d.getChilds()) {
			if (userMenus.contains(menu)) {
				beans.add(menu);
			}
		}
		return beans;
	}

	private void loadTreeChilds(SysMenu d, List<SysMenu> tree, int level,
			int levelLimit, Set<SysMenu> userMenus) {

		if ((levelLimit != -1) && (level > levelLimit)) {
			return;
		}

		boolean menu_father = true;

		for (SysMenu sm : userMenus) {

			if (sm.getId().equals(d.getId())) {

				menu_father = false;
			}
		}
		if (menu_father) {
			return;
		}

		tree.add(d);
		d.setLevel(Integer.valueOf(level));

		Iterator childs = d.getChilds().iterator();
		while (childs.hasNext()) {
			SysMenu child = (SysMenu) childs.next();

			loadTreeChilds(child, tree, level + 1, levelLimit, userMenus);
		}
	}

}
