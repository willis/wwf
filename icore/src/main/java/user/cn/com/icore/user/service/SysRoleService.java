package cn.com.icore.user.service;

import java.util.List;

import com.fins.gt.server.GridServerHandler;

import cn.com.icore.user.model.SysMenu;
import cn.com.icore.user.model.SysRole;

public interface SysRoleService {

	public static final String ID_NAME = "sysRoleService";

	public GridServerHandler listToGrid(
			GridServerHandler paramGridServerHandler, SysRole paramSysRole);

	public List<SysMenu> getSysMenusByRoleId(long paramLong);

	public List<SysRole> getByGrid(GridServerHandler paramGridServerHandler,
			SysRole paramSysRole);

	public SysRole getSysRole(long paramSerializable);

	public void saveSysRole(SysRole sysRole);

	public void removeSysRole(long paramSerializable);

	public void removeSysRole(SysRole paramSysRole);

	public int count();

	public List<SysRole> getSysRoles(int paramInt1, int paramInt2);

	public void addSysMenu(long paramLong, String[] paramArrayOfString);

	public void updateSysRole(SysRole paramSysRole);

	public GridServerHandler listCheckPopedomsToGrid(
			GridServerHandler paramGridServerHandler, SysRole paramSysRole);

	public GridServerHandler listNotCheckPopedomsToGrid(
			GridServerHandler paramGridServerHandler, SysRole paramSysRole);

	public void addSysPopedom(long paramLong, String[] paramArrayOfString);

	public void removeSysPopedom(long paramLong, String[] paramArrayOfString);

}
