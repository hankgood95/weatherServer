package com.wook.model.dto;

public class GeoInfo {

	private int geoNum;
	private int x;
	private int y;
	
	public GeoInfo() {
		super();
	}
	
	public GeoInfo(int geoNum, int x, int y) {
		super();
		this.geoNum = geoNum;
		this.x = x;
		this.y = y;
	}

	public int getGeoNum() {
		return geoNum;
	}
	public void setGeoNum(int geoNum) {
		this.geoNum = geoNum;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "GeoInfo [geoNum=" + geoNum + ", x=" + x + ", y=" + y + "]";
	}
	
	
	
}
