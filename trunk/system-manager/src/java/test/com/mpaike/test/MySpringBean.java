package com.mpaike.test;

import java.io.Serializable;

public class MySpringBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "MySpringBean[id="+ id + ",name=" + name + "]";
	}
}
