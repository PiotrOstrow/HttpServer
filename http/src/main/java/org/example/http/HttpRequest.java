package org.example.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

	private HttpMethod method;
	private String address;

	private final Map<String, String> headers = new HashMap<>();
	private final Map<String, Parameter> parameters = new HashMap<>();

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

	public Parameter getParameter(String name) {
		return parameters.get(name);
	}

	public String getParameterString(String name) {
		Parameter parameter = parameters.get(name);
		if(parameter == null)
			return "";
		return parameter.asString();
	}

	protected void addHeader(String header, String value) {
		headers.put(header, value);
	}

	public void addParameter(String name, Parameter parameter) {
		parameters.put(name, parameter);
	}

	public void addParameter(String name, String parameter) {
		parameters.put(name, new Parameter(name, parameter));
	}
}
