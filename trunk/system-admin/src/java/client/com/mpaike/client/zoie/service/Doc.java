package com.mpaike.client.zoie.service;

import java.io.Serializable;

import net.sf.json.JSONSerializer;



public class Doc implements Cloneable,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5490227489225893900L;
	public static final String CONTENT_IS_PIC = "1";
	public static final String CONTENT_NOT_PIC = "0";
	
	private String pictureId;
	private String geneContent;
	private String geneDefaultValue;
	private String filename;
	private String fileType;
	private String fileSize;
	private String srcHeight;
	private String srcWidth;
	private String url;
	private String exifVersion;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPictureId() {
		return pictureId;
	}
	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}
	public String getGeneContent() {
		return geneContent;
	}
	public void setGeneContent(String geneContent) {
		this.geneContent = geneContent;
	}
	public String getGeneDefaultValue() {
		return geneDefaultValue;
	}
	public void setGeneDefaultValue(String geneDefaultValue) {
		this.geneDefaultValue = geneDefaultValue;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getSrcHeight() {
		return srcHeight;
	}
	public void setSrcHeight(String srcHeight) {
		this.srcHeight = srcHeight;
	}
	public String getSrcWidth() {
		return srcWidth;
	}
	public void setSrcWidth(String srcWidth) {
		this.srcWidth = srcWidth;
	}
	public String getExifVersion() {
		return exifVersion;
	}
	public void setExifVersion(String exifVersion) {
		this.exifVersion = exifVersion;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int hashcode(){
		if(this.pictureId==null){
			return 0;
		}
		return this.pictureId.hashCode();
	}
	public boolean equals(Object obj){
		if(obj==null){
			return false;
		}else if(obj instanceof Doc){
			return this.pictureId.equals(((Doc)obj).pictureId);
		}else{
			return false;
		}
	}
	
	


	public Doc clone(){
		Doc doc = new Doc();
		doc.setGeneDefaultValue(this.geneDefaultValue);
		doc.setGeneContent(this.geneContent);
		doc.setUrl(this.url);
		doc.setExifVersion(this.exifVersion);
		doc.setSrcHeight(this.srcHeight);
		doc.setSrcWidth(this.srcWidth);
		doc.setFilename(this.filename);
		doc.setFileSize(this.fileSize);
		doc.setFileType(this.fileType);
		doc.setPictureId(this.pictureId);
		return doc;
	}
	
	public String toString(){
		return JSONSerializer.toJSON(this).toString();
	}
	
}
