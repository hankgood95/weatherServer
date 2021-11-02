package com.wook.model;

import org.springframework.stereotype.Component;

@Component
public class Response {
    private Header header;
    private Body body;
	public Header getHeader() {
		return header;
	}
	public Body getBody() {
		return body;
	}
	@Override
	public String toString() {
		return "Response [header=" + header + ", body=" + body + "]";
	}
	
}
