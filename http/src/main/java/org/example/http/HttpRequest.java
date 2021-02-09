package org.example.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

	private final String method;
	private final String address;

	private final Map<String, String> headers = new HashMap<>();

	public HttpRequest(String method, String address) {
		this.method = method;
		this.address = address;
	}

	public String getHeader(String header) {
		return headers.get(header);
	}

	public String getMethod() {
		return method;
	}

	public String getAddress() {
		return address;
	}

	protected void addHeader(String header, String value) {
		headers.put(header, value);
	}
}
