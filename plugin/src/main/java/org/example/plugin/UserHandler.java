package org.example.plugin;

import org.example.db.User;
import org.example.db.UserDAO;
import org.example.db.UserDaoJDBCImpl;
import org.example.http.HttpRequest;
import org.example.http.HttpResponse;
import org.example.json.JsonConverter;
import org.example.spi.Address;
import org.example.spi.RequestHandler;

import java.util.List;

@Address("/users")
public class UserHandler implements RequestHandler {

	@Override
	public HttpResponse handleRequest(HttpRequest httpRequest) {
		UserDAO userDAO = new UserDaoJDBCImpl();
		List<User> list = userDAO.getAll();

		JsonConverter jsonConverter = new JsonConverter();
		String json = jsonConverter.convertToJson(list);

		HttpResponse response = new HttpResponse();
		response.setHeader("Content-Type", "application/json; charset=utf-8");
		response.setBody(json);

		return response;
	}
}
