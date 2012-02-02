package com.mpaike.client.zoie.service;

import org.apache.lucene.search.payloads.MaxPayloadFunction;
import org.apache.lucene.search.payloads.PayloadFunction;

public class QueryFactory {
	public final static String PICTURE_ID = "id";
	public final static String PICTURE_GENECONTENT = "geneContent";// 标签
	public final static String PICTURE_GENEDEFAULTVALUE = "geneDefaultValue";// 标签
	public final static String PICTURE_PAYLOAD = "gentPayload";// 时间payload
	
	
	public final static String PICTURE_FILENAME = "filename";// 标题
	public final static String PICTURE_FILETYPE = "fileType";// 数据源
	public final static String PICTURE_FILESIZE = "fileSize";// 文章来源
	public final static String PICTURE_SRCHEIGHT = "srcHeight";// 作者
	public final static String PICTURE_SRCWIDTH = "srcWidth";// 发布时间
	public final static String PICTURE_URL = "url";// 入库时间
	public final static String PICTURE_EXIFVERSION = "exifVersion";// 文章简介

	public final static PayloadFunction payloadFunction = new MaxPayloadFunction();
}
