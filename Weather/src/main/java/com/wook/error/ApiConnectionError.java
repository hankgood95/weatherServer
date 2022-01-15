package com.wook.error;

public class ApiConnectionError extends Exception{
	public ApiConnectionError() {}
	public ApiConnectionError(String msg) {
		super(msg);
	}
}
