package com.mpaike.affix.service;

import java.util.Map;

/**
 * 附件上传配置信息
 * @author max
 *
 * Dec 15, 2010
 */
public class AffixConfig {
	public static final String ID_NAME = "affixConfig";
	/**
	 * 附件默认存放地址
	 */
	private String baseDir;
	
	/**
	 * 附件上传的大小限制，默认为10M
	 */
	private long maxSize = 10;
	
	/**
	 * 二级目录，主要用于区分公共附件上传后，对文件的按目录分类 key:objectType,value:目录
	 */
	private Map<Integer ,String> dirs = null;
	
	
	public Map<Integer, String> getDirs() {
		return dirs;
	}
	public void setDirs(Map<Integer, String> dirs) {
		this.dirs = dirs;
	}
	public String getBaseDir() {
		return baseDir;
	}
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}
	public long getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

}
