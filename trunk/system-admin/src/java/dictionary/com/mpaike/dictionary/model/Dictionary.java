package com.mpaike.dictionary.model;

import java.util.Date;
import java.util.Set;

import com.mpaike.core.database.hibernate.AnnotationObjectKey;

@SuppressWarnings("unchecked")
public class Dictionary implements java.io.Serializable{
	
	  /**
	 * @author 陈海峰
	 * @email chenhaifeng@maxtech.com.cn
	 * @createDate 2011-1-27 上午11:09:07
	 * @description 
	 */
	  private static final long serialVersionUID = 1L;
	  @AnnotationObjectKey
	  private Long id;
	  private String name;
	  private Dictionary parentObj;
	  private String describe;
	  private Set<Dictionary> childs;
	  private Integer orderby;
	  private Date curDate;
	  private Integer flag;
	  private Integer defaultIndex;
	  private Long extendf1;
	  private Long extendf2;
	  private Integer extendf3;
	  private Integer extendf4;
	  private String extendf5;
	  private String extendf6;
	  private String extendf7;
	  private String extendf8;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the parentObj
	 */
	public Dictionary getParentObj() {
		return parentObj;
	}
	/**
	 * @param parentObj the parentObj to set
	 */
	public void setParentObj(Dictionary parentObj) {
		this.parentObj = parentObj;
	}
	/**
	 * @return the describe
	 */
	public String getDescribe() {
		return describe;
	}
	/**
	 * @param describe the describe to set
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	/**
	 * @return the childs
	 */
	public Set<Dictionary> getChilds() {
		return childs;
	}
	/**
	 * @param childs the childs to set
	 */
	public void setChilds(Set<Dictionary> childs) {
		this.childs = childs;
	}
	/**
	 * @return the orderby
	 */
	public Integer getOrderby() {
		return orderby;
	}
	/**
	 * @param orderby the orderby to set
	 */
	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}
	/**
	 * @return the curDate
	 */
	public Date getCurDate() {
		return curDate;
	}
	/**
	 * @param curDate the curDate to set
	 */
	public void setCurDate(Date curDate) {
		this.curDate = curDate;
	}
	/**
	 * @return the flag
	 */
	public Integer getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	/**
	 * @return the defaultIndex
	 */
	public Integer getDefaultIndex() {
		return defaultIndex;
	}
	/**
	 * @param defaultIndex the defaultIndex to set
	 */
	public void setDefaultIndex(Integer defaultIndex) {
		this.defaultIndex = defaultIndex;
	}
	/**
	 * @return the extendf1
	 */
	public Long getExtendf1() {
		return extendf1;
	}
	/**
	 * @param extendf1 the extendf1 to set
	 */
	public void setExtendf1(Long extendf1) {
		this.extendf1 = extendf1;
	}
	/**
	 * @return the extendf2
	 */
	public Long getExtendf2() {
		return extendf2;
	}
	/**
	 * @param extendf2 the extendf2 to set
	 */
	public void setExtendf2(Long extendf2) {
		this.extendf2 = extendf2;
	}
	/**
	 * @return the extendf3
	 */
	public Integer getExtendf3() {
		return extendf3;
	}
	/**
	 * @param extendf3 the extendf3 to set
	 */
	public void setExtendf3(Integer extendf3) {
		this.extendf3 = extendf3;
	}
	/**
	 * @return the extendf4
	 */
	public Integer getExtendf4() {
		return extendf4;
	}
	/**
	 * @param extendf4 the extendf4 to set
	 */
	public void setExtendf4(Integer extendf4) {
		this.extendf4 = extendf4;
	}
	/**
	 * @return the extendf5
	 */
	public String getExtendf5() {
		return extendf5;
	}
	/**
	 * @param extendf5 the extendf5 to set
	 */
	public void setExtendf5(String extendf5) {
		this.extendf5 = extendf5;
	}
	/**
	 * @return the extendf6
	 */
	public String getExtendf6() {
		return extendf6;
	}
	/**
	 * @param extendf6 the extendf6 to set
	 */
	public void setExtendf6(String extendf6) {
		this.extendf6 = extendf6;
	}
	/**
	 * @return the extendf7
	 */
	public String getExtendf7() {
		return extendf7;
	}
	/**
	 * @param extendf7 the extendf7 to set
	 */
	public void setExtendf7(String extendf7) {
		this.extendf7 = extendf7;
	}
	/**
	 * @return the extendf8
	 */
	public String getExtendf8() {
		return extendf8;
	}
	/**
	 * @param extendf8 the extendf8 to set
	 */
	public void setExtendf8(String extendf8) {
		this.extendf8 = extendf8;
	}

}
