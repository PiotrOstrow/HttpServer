package org.example.plugin;

import org.example.fileutil.FileReader;
import org.example.http.HttpRequest;
import org.example.http.HttpResponse;
import org.example.http.Parameter;
import org.example.spi.Address;
import org.example.spi.RequestHandler;

import java.io.File;

@Address("/upload_test.html")
public class UploadTestHandler implements RequestHandler {
	@Override
	public HttpResponse handleRequest(HttpRequest httpRequest) {
		HttpResponse response = new HttpResponse();
		response.setHeader("Content-Type", "text/html");

		byte[] data = FileReader.readFromFile(new File("htdocs/upload_test.html"));
		String body = new String(data);

		Parameter fileToUpload = httpRequest.getParameter("fileToUpload");
		if(fileToUpload != null) {
			int size = fileToUpload.getData().length;
			body = body.replace("_?", "You uploaded " + fileToUpload.getFileName() + " with a size of "  + size + " bytes<br>");
		} else {
			body = body.replace("_?", "");
		}

		response.setBody(body);

		return response;
	}
}
