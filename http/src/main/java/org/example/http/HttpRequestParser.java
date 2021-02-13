package org.example.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class HttpRequestParser {

	public static HttpRequest parse(InputStream inputStream) throws IOException {
		HttpRequest request;
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		// parse start line
		String line = reader.readLine();
		if(line == null)
			throw new IOException("Connection closed");
		String[] split = line.split(" ");

		if(split.length < 3)
			throw new RuntimeException("Invalid http header");

		request = new HttpRequest(HttpMethod.valueOf(split[0]));
		String address = split[1];

		// has GET arguments
		if(split[1].contains("?")) {
			address = split[1].substring(0, split[1].indexOf("?"));
			String args = split[1].substring(split[1].indexOf("?") + 1);
			parseParameters(request, args);
		}

		// default to index.html
		if(address.equals("/"))
			address = "/index.html";

		if(address.endsWith("/"))
			address = address.substring(0, address.length() - 1);

		request.setAddress(address);

		while((line = reader.readLine()) != null && !line.isEmpty()) {
			String[] splitHeader = line.split(":");
			request.addHeader(splitHeader[0], splitHeader[1].trim());
		}

		// TODO: read body

		// assumed application/x-www-form-urlencoded
		String contentType = request.getHeader("Content-Type");
		String contentLength = request.getHeader("Content-Length");
		if(contentLength != null) {
			char[] buffer = new char[Integer.parseInt(contentLength)];
			reader.read(buffer);
			parseParameters(request, new String(buffer));
		}

		return request;
	}


	private static void parseParameters(HttpRequest request, String parameterLine) {
		String[] args = parameterLine.split("&");
		for(String s : args) {
			int index = s.indexOf("=");
			String paramName = URLDecoder.decode(s.substring(0, index), StandardCharsets.UTF_8);
			String value = URLDecoder.decode(s.substring(index + 1), StandardCharsets.UTF_8);
			request.addParameter(paramName, value);
		}
	}
}
