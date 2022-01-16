package com.wook.error;

public class ApiCallError extends Exception{
	private String msg;
	
	public ApiCallError() {}

	public ApiCallError(String msg) {
		super(msg);
	}
}
