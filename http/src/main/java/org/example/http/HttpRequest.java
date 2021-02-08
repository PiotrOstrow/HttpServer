package org.example.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

	private final String method;
	private final String address;

	private final Map<String, String> headers = new HashMap<>();

	public HttpRequest(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		String[] split = line.split(" ");

		//GET /index.html HTTP/1.1
		if(split.length < 3)
			throw new RuntimeException("Invalid http header");

		method = split[0];

		// default to index.html
		address = split[1].equals("/") ? "index.html" : split[1];

		while((line = reader.readLine()) != null && !line.isEmpty()) {
			String[] splitHeader = line.split(":");
			headers.put(splitHeader[0], splitHeader[1].trim());
		}
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
}
