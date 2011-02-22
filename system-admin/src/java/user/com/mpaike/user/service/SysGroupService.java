package com.mpaike.user.service;

import java.util.List;
import java.util.Map;

import com.fins.gt.server.GridServerHandler;
import com.mpaike.user.model.SysGroup;
import com.mpaike.user.model.SysRole;
import com.mpaike.user.model.SysUser;


@SuppressWarnings("unchecked")
public interface SysGroupService {
	public static final String ID_NAME = "sysGroupService";

	public abstract GridServerHandler listCheckUsersToGrid(
			GridServerHandler paramGridServerHandler, SysUser paramSysUser,
			long paramLong);

	public abstract GridServerHandler listNotCheckUsersToGrid(
			GridServerHandler paramGridServerHandler, SysUser paramSysUser,
			long paramLong);

	public abstract List<Map> getSysGroupsToMap(long paramLong);

	public abstract List<Map> getSysUserByGroupIdToMap(long paramLong);

	public abstract SysGroup getSysGroup(Long paramSerializable);

	public abstract boolean removeSysGroup(Long paramSerializable);

	public abstract void removeSysGroup(SysGroup paramSysGroup);

	public abstract void saveSysGroup(SysGroup paramSysGroup);

	public abstract void addSysUserToGroup(long paramLong1, long paramLong2);

	public abstract List<SysGroup> getSysGroups(Long paramLong);

	public abstract List<String[]> getTrees(SysGroup paramSysGroup,
			List<String[]> paramList);

	public abstract void removeSysUserToGroup(long paramLong1, long paramLong2);

	public abstract List<SysGroup> findSysGroupByName(String paramString);

	public abstract List<SysRole> getSysRoles(SysGroup paramSysGroup);
}
