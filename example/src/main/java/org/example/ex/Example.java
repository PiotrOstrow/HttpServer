package org.example.ex;

import com.google.gson.Gson;
import org.example.db.User;
import org.example.db.UserDAO;
import org.example.db.UserDaoJDBCImpl;
import org.example.http.HttpRequest;
import org.example.http.HttpResponse;
import org.example.spi.Address;
import org.example.spi.RequestHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Address("/users")
public class Example implements RequestHandler {

	@Override
	public HttpResponse handleRequest(HttpRequest httpRequest) {
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
//		String body = "Custom page <br>" + dtf.format(LocalDateTime.now());

		UserDAO userDAO = new UserDaoJDBCImpl();
		List<User> list = userDAO.getAll();

		Gson gson = new Gson();
		String json = gson.toJson(list);

		HttpResponse response = new HttpResponse();
		response.setHeader("Content-Type", "application/json");
		response.setBody(json);

		return response;
	}
}
