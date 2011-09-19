package com.mpaike.user.service;

import java.util.List;
import java.util.Map;

import com.mpaike.core.util.page.Pagination;
import com.mpaike.user.model.SysGroup;
import com.mpaike.user.model.SysRole;
import com.mpaike.user.model.SysUser;


public interface SysGroupService {
	public static final String ID_NAME = "sysGroupService";

	public  List<SysUser> listCheckUsersToGrid(SysUser paramSysUser,
			long paramLong, Pagination p);

	public  List<SysUser> listNotCheckUsersToGrid(SysUser paramSysUser,
			long paramLong ,Pagination p);

	public  SysGroup getSysGroup(Long paramSerializable);

	public  boolean removeSysGroup(Long paramSerializable);

	public  void removeSysGroup(SysGroup paramSysGroup);

	public  void saveSysGroup(SysGroup paramSysGroup);

	public  void addSysUserToGroup(long paramLong1, long paramLong2);

	public  List<SysGroup> getSysGroups(Long paramLong);

	public  List<String[]> getTrees(SysGroup paramSysGroup,
			List<String[]> paramList);

	public  void removeSysUserToGroup(long paramLong1, long paramLong2);

	public  List<SysRole> getSysRoles(SysGroup paramSysGroup);
}
