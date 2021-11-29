package com.wook.model.dto;

import org.springframework.stereotype.Component;

@Component
public class ShortWeatherReq {
	
    private String serviceKey;
    private String pageNo;
    private String numOfRows;
    private String dataType;
    private String base_date;
    private String base_time;
    private int nx;
    private int ny;
    
    
    
	public ShortWeatherReq() {
		super();
	}

	public ShortWeatherReq(String serviceKey, String pageNo, String numOfRows, String dataType, String base_date,
			String base_time, int nx, int ny) {
		super();
		this.serviceKey = serviceKey;
		this.pageNo = pageNo;
		this.numOfRows = numOfRows;
		this.dataType = dataType;
		this.base_date = base_date;
		this.base_time = base_time;
		this.nx = nx;
		this.ny = ny;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getNumOfRows() {
		return numOfRows;
	}

	public void setNumOfRows(String numOfRows) {
		this.numOfRows = numOfRows;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getBase_date() {
		return base_date;
	}

	public void setBase_date(String base_date) {
		this.base_date = base_date;
	}

	public String getBase_time() {
		return base_time;
	}

	public void setBase_time(String base_time) {
		this.base_time = base_time;
	}

	public int getNx() {
		return nx;
	}

	public void setNx(int nx) {
		this.nx = nx;
	}

	public int getNy() {
		return ny;
	}

	public void setNy(int ny) {
		this.ny = ny;
	}

	@Override
	public String toString() {
		return "ShortWeatherReq [serviceKey=" + serviceKey + ", pageNo=" + pageNo + ", numOfRows=" + numOfRows
				+ ", dataType=" + dataType + ", base_date=" + base_date + ", base_time=" + base_time + ", nx=" + nx
				+ ", ny=" + ny + "]";
	}
}
