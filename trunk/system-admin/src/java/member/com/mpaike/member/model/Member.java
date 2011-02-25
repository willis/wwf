package com.mpaike.member.model;

import java.util.Date;

import com.mpaike.util.dao.IBeanPrimaryKey;
/**
 * 
 * @author 陈海峰
 * @createDate 2011-2-24 下午03:18:12
 * @description 会员表
 */
public class Member implements IBeanPrimaryKey, java.io.Serializable{
	
	/**
	 * @author 陈海峰
	 * @createDate 2011-2-24 下午02:25:06
	 * @description 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//用户id
	private String username;//用户名
	private String password;//密码
	private Integer status;//状态(是否禁用,删除)
	private String name;//昵称
	private Date birthday;//生日
	private Integer sex;//性别
	private String email;//邮箱
	private Integer city;//所在城市
	private String profession;//职业
	private Integer userStatus;//用户状态(浏览中,离线等)
	private Date lastLoginDate;//用户最后登录时间
	private Long loginCount;//用户登陆次数
	private Long photoCount;//用户总照片数
	private Long friendCount;//用户朋友数
	private Long accessCount;//被访问次数
	private String immediatelyInfo;//即时个性签名

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Long getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}

	public Long getPhotoCount() {
		return photoCount;
	}

	public void setPhotoCount(Long photoCount) {
		this.photoCount = photoCount;
	}

	public Long getFriendCount() {
		return friendCount;
	}

	public void setFriendCount(Long friendCount) {
		this.friendCount = friendCount;
	}

	public Long getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(Long accessCount) {
		this.accessCount = accessCount;
	}

	public String getImmediatelyInfo() {
		return immediatelyInfo;
	}

	public void setImmediatelyInfo(String immediatelyInfo) {
		this.immediatelyInfo = immediatelyInfo;
	}
	
	

}
