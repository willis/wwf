package cn.com.icore.user.model;

import java.util.Date;
import java.util.Set;

import cn.com.icore.util.hibernate.dao.IBeanPrimaryKey;

/**
 * 
 * @author Chen.H
 * @createDate 2010-12-16 下午03:51:03
 * @description
 */
public class SysUser  implements IBeanPrimaryKey, java.io.Serializable {
	
	/**
	 * @author 陈海峰
	 * @createDate 2010-12-16 下午04:01:31
	 * @description 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String truename;
	private String sex;
	private String email;
	private String tel;
	private String mark;
	private String ask;
	private String answer;
	private String other;
	private Date regtime;
	private Date logintime;
	private String password;
	private Long status;
	private Integer extendf1;
	private Integer extendf2;
	private Integer extendf3;
	private Integer extendf4;
	private String extendf5;
	private String extendf6;
	private String extendf7;
	private String extendf8;
	private String loginip;
	public Set<SysGroup> sysGroups;
	public Set<SysUserToSysRole> sysUserToSysRoles;
	public SysUser(){
	
	}
	

	/**
	 * @return the sysGroups
	 */
	public Set<SysGroup> getSysGroups() {
		return sysGroups;
	}


	/**
	 * @param sysGroups the sysGroups to set
	 */
	public void setSysGroups(Set<SysGroup> sysGroups) {
		this.sysGroups = sysGroups;
	}


	public SysUser(Long id, String username, String truename, String sex,
			String email, String tel, String mark, String ask, String answer,
			String other, Date regtime, Date logintime, String password,
			Long status) {

		this.id = id;
		this.username = username;
		this.truename = truename;
		this.sex = sex;
		this.email = email;
		this.tel = tel;
		this.mark = mark;
		this.ask = ask;
		this.answer = answer;
		this.other = other;
		this.regtime = regtime;
		this.logintime = logintime;
		this.password = password;
		this.status = status;

	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getRegtime() {
		return regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}

	public Date getLogintime() {
		return logintime;
	}

	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Integer getExtendf1() {
		return extendf1;
	}

	public void setExtendf1(Integer extendf1) {
		this.extendf1 = extendf1;
	}

	public Integer getExtendf2() {
		return extendf2;
	}

	public void setExtendf2(Integer extendf2) {
		this.extendf2 = extendf2;
	}

	public Integer getExtendf3() {
		return extendf3;
	}

	public void setExtendf3(Integer extendf3) {
		this.extendf3 = extendf3;
	}

	public Integer getExtendf4() {
		return extendf4;
	}

	public void setExtendf4(Integer extendf4) {
		this.extendf4 = extendf4;
	}

	public String getExtendf5() {
		return extendf5;
	}

	public void setExtendf5(String extendf5) {
		this.extendf5 = extendf5;
	}

	public String getExtendf6() {
		return extendf6;
	}

	public void setExtendf6(String extendf6) {
		this.extendf6 = extendf6;
	}

	public String getExtendf7() {
		return extendf7;
	}

	public void setExtendf7(String extendf7) {
		this.extendf7 = extendf7;
	}

	public String getExtendf8() {
		return extendf8;
	}

	public void setExtendf8(String extendf8) {
		this.extendf8 = extendf8;
	}

	/**
	 * @return the mark
	 */
	public String getMark() {
		return mark;
	}

	/**
	 * @param mark the mark to set
	 */
	public void setMark(String mark) {
		this.mark = mark;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @return the other
	 */
	public String getOther() {
		return other;
	}

	/**
	 * @param other the other to set
	 */
	public void setOther(String other) {
		this.other = other;
	}

	/**
	 * @return the ask
	 */
	public String getAsk() {
		return ask;
	}

	/**
	 * @param ask the ask to set
	 */
	public void setAsk(String ask) {
		this.ask = ask;
	}


	/**
	 * @return the sysUserToSysRoles
	 */
	public Set<SysUserToSysRole> getSysUserToSysRoles() {
		return sysUserToSysRoles;
	}


	/**
	 * @param sysUserToSysRoles the sysUserToSysRoles to set
	 */
	public void setSysUserToSysRoles(Set<SysUserToSysRole> sysUserToSysRoles) {
		this.sysUserToSysRoles = sysUserToSysRoles;
	}


	/**
	 * @return the loginip
	 */
	public String getLoginip() {
		return loginip;
	}


	/**
	 * @param loginip the loginip to set
	 */
	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}
	
	

}
