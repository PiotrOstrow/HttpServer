package org.example.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

	private HttpMethod method;
	private String address;

	private final Map<String, String> headers = new HashMap<>();
	private final Map<String, String> parameters = new HashMap<>();

	public HttpRequest(HttpMethod method) {
		this.method = method;
	}

	public HttpRequest(HttpMethod method, String address) {
		this.method = method;
		this.address = address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHeader(String header) {
		return headers.get(header);
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getAddress() {
		return address;
	}

	public String getParameter(String name) {
		return parameters.get(name);
	}

	protected void addHeader(String header, String value) {
		headers.put(header, value);
	}

	public void addParameter(String name, String value) {
		parameters.put(name, value);
	}
}
