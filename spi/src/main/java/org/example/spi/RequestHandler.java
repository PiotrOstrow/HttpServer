package org.example.spi;

import org.example.http.HttpRequest;
import org.example.http.HttpResponse;

public interface RequestHandler {
	HttpResponse handleRequest(HttpRequest httpRequest);
}
