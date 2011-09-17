package com.mpaike.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mpaike.core.util.page.Pagination;
import com.mpaike.sys.service.impl.BaseService;
import com.mpaike.user.model.SysGroup;
import com.mpaike.user.model.SysRole;
import com.mpaike.user.model.SysUser;
import com.mpaike.user.service.SysGroupService;



public class SysGroupServiceImpl extends BaseService implements
		SysGroupService {


	public List<String[]> getTrees(SysGroup sysGroup, List<String[]> trees) {
		Set<SysGroup> childGroups = sysGroup.getChildGroups();
 
		for (SysGroup cg : childGroups) {
		
			String nodeId = cg.getId().toString();
			String nodeName = cg.getName();
			String nodePId = cg.getParentGroup().getId().toString();
			if (trees == null)
				trees = new ArrayList();
			trees.add(new String[] { nodeId, nodeName, nodePId });

			if (!cg.getChildGroups().isEmpty()) {
				getTrees(cg, trees);
			}
		}

		return trees;
	}
	
	@Override
	public List<SysUser> listCheckUsersToGrid(SysUser paramSysUser,
			long paramLong, Pagination p) {
	
		return this.getSysGroupDao().listCheckUsersToGrid(paramSysUser, paramLong, p);
	}

	@Override
	public List<SysUser> listNotCheckUsersToGrid(SysUser paramSysUser,
			long paramLong, Pagination p) {
		
		return this.getSysGroupDao().listNotCheckUsersToGrid(paramSysUser, paramLong, p);
	}

	@Override
	public List<Map> getSysGroupsToMap(long paramLong) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map> getSysUserByGroupIdToMap(long paramLong) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysGroup getSysGroup(Long paramSerializable) {
		
		return this.getSysGroupDao().get(paramSerializable);
	}

	@Override
	public boolean removeSysGroup(Long paramSerializable) {
		
		this.getSysGroupDao().deleteById(paramSerializable);
		return true;
	}

	@Override
	public void removeSysGroup(SysGroup paramSysGroup) {
		this.getSysGroupDao().delete(paramSysGroup);
	}

	@Override
	public void saveSysGroup(SysGroup paramSysGroup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSysUserToGroup(long paramLong1, long paramLong2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SysGroup> getSysGroups(Long paramLong) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeSysUserToGroup(long paramLong1, long paramLong2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SysGroup> findSysGroupByName(String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysRole> getSysRoles(SysGroup paramSysGroup) {
		
		return this.getSysGroupDao().getSysRoles(paramSysGroup);
	}

}
