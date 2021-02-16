package org.example.http;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class HttpRequestParser {

	public static HttpRequest parse(InputStream inputStream) throws IOException {
		HttpRequest request;
		RequestInputStream requestInputStream = new RequestInputStream(inputStream);

		// parse start line
		String line = requestInputStream.readLine();
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
			parseStringParameters(request, args);
		}

		// default to index.html
		if(address.equals("/"))
			address = "/index.html";

		if(address.endsWith("/"))
			address = address.substring(0, address.length() - 1);

		request.setAddress(address);

		while((line = requestInputStream.readLine()) != null && !line.isEmpty()) {
			String[] splitHeader = line.split(":");
			request.addHeader(splitHeader[0], splitHeader[1].trim());
		}

		String contentLength = request.getHeader("Content-Length");
		if(contentLength != null) {
			parseParameters(request, requestInputStream);
		}

		return request;
	}

	private static void parseParameters(HttpRequest request, RequestInputStream stream) throws IOException{
		String contentType = request.getHeader("Content-Type");
		if(contentType.startsWith("multipart/form-data"))
			parseMultiFormData(request, stream);
		else if(contentType.startsWith("application/x-www-form-urlencoded"))
			parseWWWFormParametersPOST(request, stream);
		else
			throw new RuntimeException("Unsupported content type: " + contentType);
	}

	/**
	 *  parse multipart/form-data
	 *  https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Disposition
	 */
	private static void parseMultiFormData(HttpRequest request, RequestInputStream stream) throws IOException{
		String contentType = request.getHeader("Content-Type");
		String boundary = contentType.split(";")[1];
		boundary = "--" + boundary.substring(boundary.indexOf("=") + 1);
		String endBoundary = boundary + "--";

		Parameter parameter = new Parameter();
		boolean eof = false;

		// First line should be boundary
		String line = stream.readLine();
		// Next lines should be content-type or content-disposition, read until empty line
		while(!eof && !(line = stream.readLine()).contains(endBoundary)) {
			if(!line.isEmpty()){
				parseMultipartHeaders(parameter, line);
			} else { // if line is empty, value is next
				// if content-type is not set, assume plain text line?
				if(parameter.getContentType().isEmpty()) {
					parameter.setData(stream.readLine().getBytes());
				} else {
					eof = stream.readParamData(boundary, parameter);
				}
				request.addParameter(parameter.getName(), parameter);
				parameter = new Parameter();
			}
		}
	}

	private static void parseMultipartHeaders(Parameter parameter, String line) {
		// Content-Disposition: form-data; name="fileToUpload"; filename="cat.png"
		// Content-Type: image/png
		String[] split = line.split(":");

		if(line.startsWith("Content-Disposition")){
			split = split[1].split("; ");
			for(String s : split) {
				String[] pair = s.split("=");
				if(pair.length < 2)
					continue;
				String value = pair[1].replaceAll("\"", "");

				switch(pair[0]){
					case "name": 	 parameter.setName(value);     break;
					case "filename": parameter.setFileName(value); break;
				}
			}
		} else if(line.startsWith("Content-Type")) {
			parameter.setContentType(split[1].trim());
		}
	}

	/**
	 * parse application/x-www-form-urlencoded
	 */
	private static void parseWWWFormParametersPOST(HttpRequest request, RequestInputStream stream) throws IOException{
		String contentLength = request.getHeader("Content-Length");
		byte[] buffer = new byte[Integer.parseInt(contentLength)];
		stream.read(buffer);
		String parameterLine = new String(buffer);
		parseStringParameters(request, parameterLine);
	}

	private static void parseStringParameters(HttpRequest request, String parameterLine) {
		String[] args = parameterLine.split("&");
		for(String s : args) {
			int index = s.indexOf("=");
			String paramName = URLDecoder.decode(s.substring(0, index), StandardCharsets.UTF_8);
			String value = URLDecoder.decode(s.substring(index + 1), StandardCharsets.UTF_8);
			request.addParameter(paramName, value);
		}
	}
}
