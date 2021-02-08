package org.example;

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
		System.out.println(line);
		String[] split = line.split(" ");

		//GET /index.html HTTP/1.1
		if(split.length < 3)
			throw new RuntimeException("Invalid http header");

		method = split[0];
		address = split[1];

		while((line = reader.readLine()) != null && !line.isEmpty()) {
			String[] splitHeader = line.split(":");

			System.out.println(line);
			headers.put(splitHeader[0], splitHeader[1].trim());
		}
		System.out.println();
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
