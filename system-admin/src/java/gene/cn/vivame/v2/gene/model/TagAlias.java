package cn.vivame.v2.gene.model;

import net.sf.json.JSONSerializer;

public class TagAlias {

	private String tagName;
	private String tagAliasName;
	private int commend;
	
	public String getTagkey() {
		StringBuilder sb = new StringBuilder();
		 sb.append(tagName.trim()).append("-").append(tagAliasName.trim());
		return sb.toString().toLowerCase();
	}

	public void setTagkey(String tagkey) {
	}

	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		if(tagName!=null){
			this.tagName = tagName.trim();
		}else{
			this.tagName = null;
		}
	}
	public String getTagAliasName() {
		return tagAliasName;
	}
	public void setTagAliasName(String tagAliasName) {
		if(tagAliasName!=null){
			this.tagAliasName = tagAliasName.trim();
		}else{
			this.tagAliasName = null;
		}
	}

	public int getCommend() {
		return commend;
	}

	public void setCommend(int commend) {
		this.commend = commend;
	}

	public String toString(){
		return JSONSerializer.toJSON(this).toString();
	}

}
