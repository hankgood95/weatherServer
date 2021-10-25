package com.wook.model;

import org.springframework.stereotype.Component;

@Component
public class SweatherRootRes {
	private Response response;

	public Response getResponse() {
		return response;
	}
}
