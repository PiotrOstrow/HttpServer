package org.example.ex;

import org.example.http.HttpRequest;
import org.example.http.HttpResponse;
import org.example.spi.Address;
import org.example.spi.RequestHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Address("/Example")
public class Example implements RequestHandler {

	@Override
	public HttpResponse handleRequest(HttpRequest httpRequest) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
		String body = "Custom page <br>" + dtf.format(LocalDateTime.now());

		HttpResponse response = new HttpResponse();
		response.setHeader("Content-Type", "text/html");
		response.setBody(body);

		return response;
	}
}
