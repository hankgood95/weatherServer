package com.wook.model.dto;

import org.springframework.stereotype.Component;

@Component
public class Temperature {
	
	private String tempKey;
	private int bestMin;
	private int bestMax;
	private String date;
	private int nx;
	private int ny;
	
	
	
	public Temperature() {
		super();
	}
	
	public Temperature(String tempKey, int bestMin, int bestMax, String date, int nx, int ny) {
		super();
		this.tempKey = tempKey;
		this.bestMin = bestMin;
		this.bestMax = bestMax;
		this.date = date;
		this.nx = nx;
		this.ny = ny;
	}




	public String getTempKey() {
		return tempKey;
	}



	public void setTempKey(String tempKey) {
		this.tempKey = tempKey;
	}



	public int getBestMin() {
		return bestMin;
	}
	public void setBestMin(int bestMin) {
		this.bestMin = bestMin;
	}
	public int getBestMax() {
		return bestMax;
	}
	public void setBestMax(int bestMax) {
		this.bestMax = bestMax;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
		return "Temperature [tempKey=" + tempKey + ", bestMin=" + bestMin + ", bestMax=" + bestMax + ", date=" + date
				+ ", nx=" + nx + ", ny=" + ny + "]";
	}
	
	
}
