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
	private Long status;//状态(1=正常,2=禁用,3=删除)
	private String name;//昵称
	private Date birthday;//生日
	private Integer sex;//性别
	private String email;//邮箱
	private Integer city;//所在城市
	private String profession;//职业
	private Integer userStatus;//用户状态(1=在线,2=离线,3=发表中)
	private Date lastLoginDate;//用户最后登录时间
	private Long loginCount;//用户登陆次数
	private Long photoCount;//用户总照片数
	private Long friendCount;//用户朋友数
	private Long accessCount;//被访问次数
	private String immediatelyInfo;//即时个性签名
	private Long fattCount;//被关注的数量
	private Long myattCount;//我关注别人的数量
	private Long points;//用户点数
	private Long userLevel;//用户级别
	private Long outPoints; //我花费的点数
	private Long userMoney;//用户币
    private Long outMoney;//用户用户花费的币
	private Date createdDate;//创建日期

	private MemberInfo memberInfo;//用户介绍
	

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}



	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}



	public Member() {
		super();
	}


	public Member(Long id, String username, String password, Long status,
			String name, Date birthday, Integer sex, String email,
			Integer city, String profession, Integer userStatus,
			Date lastLoginDate, Long loginCount, Long photoCount,
			Long friendCount, Long accessCount, String immediatelyInfo,
			Long fattCount, Long myattCount, Long points, Long userLevel,
			Long outPoints, Long userMoney, Long outMoney, Date createdDate) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.status = status;
		this.name = name;
		this.birthday = birthday;
		this.sex = sex;
		this.email = email;
		this.city = city;
		this.profession = profession;
		this.userStatus = userStatus;
		this.lastLoginDate = lastLoginDate;
		this.loginCount = loginCount;
		this.photoCount = photoCount;
		this.friendCount = friendCount;
		this.accessCount = accessCount;
		this.immediatelyInfo = immediatelyInfo;
		this.fattCount = fattCount;
		this.myattCount = myattCount;
		this.points = points;
		this.userLevel = userLevel;
		this.outPoints = outPoints;
		this.userMoney = userMoney;
		this.outMoney = outMoney;
		this.createdDate = createdDate;
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
	
	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getFattCount() {
		return fattCount;
	}


	public void setFattCount(Long fattCount) {
		this.fattCount = fattCount;
	}

	public Long getMyattCount() {
		return myattCount;
	}

	public void setMyattCount(Long myattCount) {
		this.myattCount = myattCount;
	}

	public Long getPoints() {
		return points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

	public Long getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Long userLevel) {
		this.userLevel = userLevel;
	}



	public Long getOutPoints() {
		return outPoints;
	}



	public void setOutPoints(Long outPoints) {
		this.outPoints = outPoints;
	}



	public Long getUserMoney() {
		return userMoney;
	}



	public void setUserMoney(Long userMoney) {
		this.userMoney = userMoney;
	}



	public Long getOutMoney() {
		return outMoney;
	}



	public void setOutMoney(Long outMoney) {
		this.outMoney = outMoney;
	}

}
