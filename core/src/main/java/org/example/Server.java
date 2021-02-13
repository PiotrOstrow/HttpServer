package org.example;

import org.example.http.HttpRequest;
import org.example.http.HttpRequestParser;
import org.example.http.HttpResponse;
import org.example.spi.RequestHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static void main(String[] args) throws IOException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		ServerSocket serverSocket = new ServerSocket(80);

		while(true) {
			Socket socket = serverSocket.accept();

			executorService.execute(() -> handleConnection(socket));
		}
	}

	private static void handleConnection(Socket socket) {
		try {
			HttpRequest request = HttpRequestParser.parse(socket.getInputStream());
			RequestHandler requestHandler = ServiceProvider.getRequestHandler(request);
			HttpResponse httpResponse = requestHandler.handleRequest(request);
			sendResponse(socket, httpResponse);
		} catch (IOException | RuntimeException e) {
			e.printStackTrace();
		}
	}

	private static void sendResponse(Socket socket, HttpResponse response) throws IOException{
		PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

		printWriter.println(response.getStatusLine());
		for(Map.Entry<String, String> header: response.getHeaders().entrySet())
			printWriter.println(header.getKey() + ": " + header.getValue());
		printWriter.println("");
		printWriter.flush();

		socket.getOutputStream().write(response.getBody());
		socket.getOutputStream().flush();

		// probably remove this line if one connection is to handle multiple requests
		//socket.getOutputStream().close();
	}
}
