package org.example;

import org.example.http.HttpRequest;
import org.example.spi.Method;
import org.example.spi.RequestHandler;

import java.util.Optional;
import java.util.ServiceLoader;

public class ServiceProvider {

	public static RequestHandler getRequestHandler(HttpRequest httpRequest) {
		ServiceLoader<RequestHandler> serviceLoader = ServiceLoader.load(RequestHandler.class);

		// TODO: look for @Address first

		// look for method handler
		Optional<RequestHandler> handler = serviceLoader.stream().filter(p -> p.type().isAnnotationPresent(Method.class)
				&& p.type().getAnnotation(Method.class).value().equalsIgnoreCase(httpRequest.getMethod()))
				.map(ServiceLoader.Provider::get).findFirst();

		if(handler.isPresent())
			return handler.get();

		return null;
	}
}
