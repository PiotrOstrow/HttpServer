package org.example.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequestParser {

	public static HttpRequest parse(InputStream inputStream) throws IOException {
		HttpRequest request;
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		String line = reader.readLine();
		String[] split = line.split(" ");

		if(split.length < 3)
			throw new RuntimeException("Invalid http header");

		String method = split[0];

		// default to index.html
		String address = split[1].equals("/") ? "index.html" : split[1];

		request = new HttpRequest(method, address);

		while((line = reader.readLine()) != null && !line.isEmpty()) {
			String[] splitHeader = line.split(":");
			request.addHeader(splitHeader[0], splitHeader[1].trim());
		}

		// TODO: read body

		String contentLength = request.getHeader("Content-Length");
		if(contentLength != null){
			byte[] buffer = new byte[Integer.parseInt(contentLength)];

			System.out.println(new String(buffer));
		}

		return request;
	}
}
