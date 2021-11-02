package com.wook.model;

import org.springframework.stereotype.Component;

@Component
public class Header {
    private String resultCode;
    private String resultMsg;
	public String getResultCode() {
		return resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	@Override
	public String toString() {
		return "Header [resultCode=" + resultCode + ", resultMsg=" + resultMsg + "]";
	}
	
}
