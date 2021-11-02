package com.wook.model;

import org.springframework.stereotype.Component;

@Component
public class Body {
    private String dataType;
    private Items items;
    private int pageNo;
    private int numOfRows;
    private int totalCount;
    
	public String getDataType() {
		return dataType;
	}
	public Items getItems() {
		return items;
	}
	public int getPageNo() {
		return pageNo;
	}
	public int getNumOfRows() {
		return numOfRows;
	}
	public int getTotalCount() {
		return totalCount;
	}
	@Override
	public String toString() {
		return "Body [dataType=" + dataType + ", items=" + items + ", pageNo=" + pageNo + ", numOfRows=" + numOfRows
				+ ", totalCount=" + totalCount + "]";
	}
	
}
