package cn.vivame.v2.gene.model;

public class GeneGrade  implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID =  -4153398943549747726l;
	private String tagName;
	private Double count;
	
	public GeneGrade(String tagName, Double count) {
		this.tagName = tagName;
		this.count = count;
	}

	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	public String toString(){
		return tagName+"="+count;
	}

}
