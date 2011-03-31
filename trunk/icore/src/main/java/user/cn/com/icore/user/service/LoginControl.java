package cn.com.icore.user.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import cn.com.icore.user.model.SysGroup;
import cn.com.icore.user.model.SysMenu;
import cn.com.icore.user.model.SysPopedom;
import cn.com.icore.user.model.SysRole;
import cn.com.icore.user.model.SysUser;
import cn.com.icore.util.MD5;


@SuppressWarnings("unchecked")
public class LoginControl {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginControl.class);
	public static final String ID_NAME = "loginControl";
	public static final String LOGIN_NAME = "LOGIN_NAME";
	public static final String TRUENAME_NAME = "TRUENAME_NAME";
	public static final String POPEDOM_OBJ = "POPEDOM_OBJ";
	public static final String USER_OBJ = "userOBJ";
	public static final String SYSMENU_OBJ = "SYSMENU_OBJ";
	private SysUserService sysUserService = null;
	private SysGroupService sysGroupService = null;

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setSysGroupService(SysGroupService sysGroupService) {
		this.sysGroupService = sysGroupService;
	}
	public  static synchronized  SysUser getOperater() {
		return (SysUser) TransactionSynchronizationManager
				.getResource(LoginControl.USER_OBJ);
	}

	public boolean login(String username, String password,
			HttpServletRequest req) {
		boolean ok = false;

		if ((username == null) || (password == null)) {
			return ok;
		}
		try {
			SysUser sysUser = this.sysUserService.loginUserByPassword(username,
					MD5.toMD5(password));

			if (sysUser == null) {

				return ok;
			}
			HttpSession session = req.getSession();

			// 将用户拥有的角色取出来
			List<SysRole> u_roles = sysUserService.getSysRoles(sysUser);

			// 将用户所在的组的权限也取出来
			for (SysGroup sysGroup : sysUser.getSysGroups()) {
				u_roles.addAll(sysGroupService.getSysRoles(sysGroup));
			}

			// 定义一个角色集合
			Set<SysRole> s_roles = new HashSet<SysRole>();

			// 定久一个Set集合来装没有重复的角色
			for (int i = 0; i < u_roles.size(); i++) {
				// 将角色进行不重复过滤
				s_roles.add(u_roles.get(i));
			}

			// 定义一个Set集合来装没有重复的权限CODE
			Set<String> s_popedoms = new HashSet<String>();

			// 定义一个Set集合来装没有重复的菜单
			Set<SysMenu> sysMenus = new HashSet<SysMenu>();

			// 将用户所拥有的权限进行过滤
		
			for (SysRole sr : s_roles) {

				// 找出当前角色下的权限
				// 将该角下的权限进行填充

				Set<SysPopedom> s_p = sr.getSysPopedoms();
				for (SysPopedom sp : s_p) {
					// 将权限进行不重复过滤
					s_popedoms.add(sp.getCode());
				}
				// 添加用户登录后的菜单

				sysMenus.addAll(sr.getSysMenus());
			}

			session.setAttribute(LOGIN_NAME, sysUser.getUsername());
			session.setAttribute(TRUENAME_NAME, sysUser.getTruename());
			session.setAttribute(POPEDOM_OBJ, s_popedoms);
			session.setAttribute(USER_OBJ, sysUser);
			session.setAttribute(SYSMENU_OBJ, sysMenus);
			ok = true;
			sysUser.setLogintime(new Date());
			sysUser.setLoginip(req.getRemoteAddr());
			this.sysUserService.save(sysUser);
			TransactionSynchronizationManager.bindResource(USER_OBJ,sysUser);
			

			return ok;
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		
		}
		return ok;
	}

	public static synchronized boolean checkPopedom(String code,
			HttpServletRequest req) {
		if ((Set) req.getSession().getAttribute(POPEDOM_OBJ) == null)
			return false;
		return ((Set) req.getSession().getAttribute(POPEDOM_OBJ))
				.contains(code);
	}

	public static synchronized boolean checkPopedom(String[] codes,
			HttpServletRequest req) {
		String[] arrayOfString = codes;
		int j = codes.length;
		for (int i = 0; i < j; i++) {
			String code = arrayOfString[i];
			if (code.trim().length() == 0)
				continue;
			if (checkPopedom(code, req))
				return true;
		}
		return false;
	}

	public static synchronized void loginOut(HttpServletRequest req) {
		req.getSession().removeAttribute(LOGIN_NAME);
		req.getSession().removeAttribute(TRUENAME_NAME);
		req.getSession().removeAttribute(POPEDOM_OBJ);
		req.getSession().removeAttribute(USER_OBJ);
		req.getSession().removeAttribute(SYSMENU_OBJ);
		req.getSession().invalidate();
		try{
			TransactionSynchronizationManager.unbindResource(USER_OBJ);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static synchronized boolean hasLogin(HttpServletRequest req) {
		return getSysUser(req) != null;
	}

	public static synchronized String getLoginName(HttpServletRequest req) {
		return (String) req.getSession().getAttribute(LOGIN_NAME);
	}

	public static synchronized String getTrueName(HttpServletRequest req) {
		return (String) req.getSession().getAttribute(TRUENAME_NAME);
	}

	public static synchronized Set<String> getPopedoms(HttpServletRequest req) {
		return (Set) req.getSession().getAttribute(POPEDOM_OBJ);
	}

	public static synchronized SysUser getSysUser(HttpServletRequest req) {
		return (SysUser) req.getSession().getAttribute(USER_OBJ);
	}

	public static synchronized SysGroup getSysGroup(HttpServletRequest req) {
		return (SysGroup) ((SysUser) req.getSession().getAttribute(USER_OBJ))
				.getSysGroups().iterator().next();
	}
	
	public static synchronized Long getSysUserId(HttpServletRequest req) {
		return getSysUser(req).getId();
	}

	public static synchronized Long getSysGroupId(HttpServletRequest req) {
		return getSysGroup(req).getId();
	}

	public static synchronized String getSysGroupName(HttpServletRequest req) {
		return getSysGroup(req).getName();
	}

	public static synchronized Set<SysMenu> getSysMenus(HttpServletRequest req) {
		return (Set) req.getSession().getAttribute(SYSMENU_OBJ);
	}

}
