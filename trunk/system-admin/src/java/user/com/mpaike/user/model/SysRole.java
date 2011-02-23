package com.mpaike.user.model;

import java.util.Set;

import com.mpaike.util.dao.IBeanPrimaryKey;


/**
 * 
 * @author coolmind
 * 
 */
public class SysRole implements IBeanPrimaryKey, java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String describe;
	private Set<SysMenu> sysMenus;
	private Set<SysPopedom> sysPopedoms;
	private String popedoms;

	/**
	 * @return the popedoms
	 */
	public String getPopedoms() {
		return popedoms;
	}

	/**
	 * @param popedoms
	 *            the popedoms to set
	 */
	public void setPopedoms(String popedoms) {
		this.popedoms = popedoms;
	}

	public SysRole() {

	}

	public SysRole(Long id, String name, String describe) {
		this.id = id;
		this.name = name;
		this.describe = describe;
		this.popedoms = "";
		StringBuffer sb = new StringBuffer();
		if (getSysPopedoms() != null) {
			for (SysPopedom sysPopedom : getSysPopedoms()) {
				if (sb.length() > 0)
					sb.append("|");
				sb.append(sysPopedom.getDescribe());
			}
			this.popedoms = sb.toString();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Set<SysMenu> getSysMenus() {
		return sysMenus;
	}

	public void setSysMenus(Set<SysMenu> sysMenus) {
		this.sysMenus = sysMenus;
	}

	public Set<SysPopedom> getSysPopedoms() {
		return sysPopedoms;
	}

	public void setSysPopedoms(Set<SysPopedom> sysPopedoms) {
		this.sysPopedoms = sysPopedoms;
	}

}
