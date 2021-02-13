package org.example;

import org.example.http.HttpRequest;
import org.example.http.HttpResponse;
import org.example.spi.Address;
import org.example.spi.Method;
import org.example.spi.RequestHandler;

import java.util.Arrays;
import java.util.Optional;
import java.util.ServiceLoader;

public class ServiceProvider {

	public static RequestHandler getRequestHandler(HttpRequest httpRequest) {
		ServiceLoader<RequestHandler> serviceLoader = ServiceLoader.load(RequestHandler.class);

		// look for address handler first
		Optional<RequestHandler> handler = serviceLoader.stream().filter(p -> p.type().isAnnotationPresent(Address.class)
				&& p.type().getAnnotation(Address.class).value().equalsIgnoreCase(httpRequest.getAddress()))
				.map(ServiceLoader.Provider::get).findFirst();

		if(handler.isPresent())
			return handler.get();

		// look for method handler
		handler = serviceLoader.stream().filter(p -> p.type().isAnnotationPresent(Method.class)
				&& Arrays.stream(p.type().getAnnotation(Method.class).value()).anyMatch(httpRequest.getMethod()::equalsIgnoreCase))
				.map(ServiceLoader.Provider::get).findFirst();

		return handler.orElse(httpRequest1 -> new HttpResponse(404, "Not found"));

	}
}
