package org.example.method;

import org.example.fileutil.FileReader;
import org.example.http.HttpMethod;
import org.example.http.HttpRequest;
import org.example.http.HttpResponse;
import org.example.spi.Method;
import org.example.spi.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Method({HttpMethod.GET, HttpMethod.HEAD, HttpMethod.POST})
public class MethodHandler implements RequestHandler {

	@Override
	public HttpResponse handleRequest(HttpRequest request) {
		File file = new File("htdocs" + File.separator + request.getAddress());
		try {
			if (file.exists()) {
				HttpResponse response = new HttpResponse();
				String contentType = Files.probeContentType(file.toPath());
				response.setHeader("Content-type", contentType);

				if(!request.getMethod().equalsIgnoreCase("HEAD"))
					response.setBody(FileReader.readFromFile(file));

				return response;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new HttpResponse(404, "Not Found");
	}
}
