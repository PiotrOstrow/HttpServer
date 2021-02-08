package org.example;

import org.example.fileutils.FileReader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static void main(String[] args) throws IOException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		ServerSocket serverSocket = new ServerSocket(80);

		while(true){
			Socket socket = serverSocket.accept();

			executorService.execute(() -> handleConnection(socket));
		}
	}

	private static void handleConnection(Socket socket) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			HttpRequest request = new HttpRequest(bufferedReader);

			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

			try {
				System.out.println( new File(Server.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

			System.out.println(new File(".").getAbsolutePath());
			File file = new File("htdocs" + File.separator + request.getAddress());

			if(file.exists()){
				String contentType = Files.probeContentType(file.toPath());
				printWriter.println("HTTP/1.1 200 OK");
				printWriter.println("Content-type: " + contentType);
				printWriter.println("");
				printWriter.flush();
				socket.getOutputStream().write(FileReader.readFromFile(file));
			} else {

			}

			printWriter.flush();

			printWriter.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}


}
