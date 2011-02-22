package com.mpaike.core.util.page;

import java.util.List;

public class RecordInfo {
	
	private long startRow;
	private long endRow;
	private long totalRows;
	private List list = java.util.Collections.EMPTY_LIST;
	
	public long getStartRow() {
		if(startRow<0)
			return 0;
		return startRow;
	}
	public void setStartRow(long startRow) {
		this.startRow = startRow;
	}
	public long getEndRow() {
		if(endRow>totalRows)
			return totalRows;
		return endRow;
	}
	public void setEndRow(long endRow) {
		this.endRow = endRow;
	}
	public long getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}
	public int resultCount(){
		int c = (int)(endRow-startRow);
		if(c>0){
			return c;
		}else{
			return 0;
		}
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	
	

}
