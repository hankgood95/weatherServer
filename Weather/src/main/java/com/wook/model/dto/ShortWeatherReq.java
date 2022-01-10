package com.wook.model.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ShortWeatherReq {
	
    private String serviceKey;
    private String pageNo;
    private final String numOfRows = "290";
    private final String dataType = "JSON";
    private String base_date;
    private final String base_time="2300";
    private int nx;
    private int ny;
    
    
    
	public ShortWeatherReq() {
		super();
	}
	
	public ShortWeatherReq(String serviceKey, String pageNo, int nx, int ny) {
		super();
		this.serviceKey = serviceKey;
		this.pageNo = pageNo;
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

	public String getNumOfRows() {
		return numOfRows;
	}

	public String getDataType() {
		return dataType;
	}

	public String getBase_date() {
		return base_date;
	}

	public String getBase_time() {
		return base_time;
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

	//현재 날짜를 구해와서 baseDate에 저장하는 방식
	public void setBaseDate() {
		// TODO Auto-generated method stub
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		
		Date date = calendar.getTime();
		base_date = sdf.format(date);
		
	}


}
