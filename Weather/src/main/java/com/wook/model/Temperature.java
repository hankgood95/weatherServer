package com.wook.model;

import org.springframework.stereotype.Component;

@Component
public class Temperature {
	
	private String bestMin;
	private String bestMax;
	private String date;
	private int nx;
	private int ny;
	
	public String getBestMin() {
		return bestMin;
	}
	public void setBestMin(String bestMin) {
		this.bestMin = bestMin;
	}
	public String getBestMax() {
		return bestMax;
	}
	public void setBestMax(String bestMax) {
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
		return "Temperature [bestMin=" + bestMin + ", bestMax=" + bestMax + ", date=" + date + ", nx=" + nx + ", ny="
				+ ny + "]";
	}
	
	
}
