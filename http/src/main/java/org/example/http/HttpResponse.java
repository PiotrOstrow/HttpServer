package org.example.http;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

	private final String statusLine;
	private final Map<String, String> headers = new HashMap<>();

	private byte[] body = new byte[0];

	public HttpResponse() {
		this(200, "OK");
	}

	public HttpResponse(int statusCode, String statusText) {
		statusLine = "HTTP/1.1 " + statusCode + " " + statusText;
	}

	public void setHeader(String header, String value) {
		headers.put(header, value);
	}

	public void setBody(byte[] data) {
		body = data;
		setHeader("Content-Length", String.valueOf(data.length));
	}

	public void setBody(String data) {
		setBody(data.getBytes(StandardCharsets.UTF_8));
	}

	public String getStatusLine() {
		return statusLine;
	}

	public Map<String, String> getHeaders() {
		return Collections.unmodifiableMap(headers);
	}

	public byte[] getBody() {
		return body;
	}
}
