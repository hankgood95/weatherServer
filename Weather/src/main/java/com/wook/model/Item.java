package com.wook.model;

import org.springframework.stereotype.Component;

@Component
public class Item {
    private String baseDate;
    private String baseTime;
    private String category;
    private String fcstDate;
    private String fcstTime;
    private String fcstValue;
    private int nx;
    private int ny;
    
	public String getBaseDate() {
		return baseDate;
	}
	public String getBaseTime() {
		return baseTime;
	}
	public String getCategory() {
		return category;
	}
	public String getFcstDate() {
		return fcstDate;
	}
	public String getFcstTime() {
		return fcstTime;
	}
	public String getFcstValue() {
		return fcstValue;
	}
	public int getNx() {
		return nx;
	}
	public int getNy() {
		return ny;
	}
	@Override
	public String toString() {
		return "Item [baseDate=" + baseDate + ", baseTime=" + baseTime + ", category=" + category + ", fcstDate="
				+ fcstDate + ", fcstTime=" + fcstTime + ", fcstValue=" + fcstValue + ", nx=" + nx + ", ny=" + ny + "]";
	}
	
	
}
