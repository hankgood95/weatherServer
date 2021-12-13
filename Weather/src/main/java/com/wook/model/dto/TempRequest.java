package com.wook.model.dto;

import org.springframework.stereotype.Component;

@Component
public class TempRequest {

	private int x;
	private int y;
	private String baseDate;
	private String tempKey;	
	
	public TempRequest(int x, int y, String baseDate, String tempKey) {
		super();
		this.x = x;
		this.y = y;
		this.baseDate = baseDate;
		this.tempKey = tempKey;
	}

	public String getTempKey() {
		return tempKey;
	}

	public void setTempKey(String tempKey) {
		this.tempKey = tempKey;
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

	public String getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}

	@Override
	public String toString() {
		return "TempRequest [x=" + x + ", y=" + y + ", baseDate=" + baseDate + ", tempKey=" + tempKey + "]";
	}
}
