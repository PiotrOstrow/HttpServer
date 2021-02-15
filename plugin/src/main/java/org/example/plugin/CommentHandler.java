package org.example.plugin;

import org.example.db.Comment;
import org.example.db.CommentDAO;
import org.example.db.CommentDaoJPAImpl;
import org.example.fileutil.FileReader;
import org.example.http.HttpRequest;
import org.example.http.HttpResponse;
import org.example.spi.Address;
import org.example.spi.RequestHandler;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Address("/comments.html")
public class CommentHandler implements RequestHandler {

	private CommentDAO commentDao;

	@Override
	public HttpResponse handleRequest(HttpRequest httpRequest) {
		commentDao = new CommentDaoJPAImpl();
		saveComment(httpRequest);

		HttpResponse response = new HttpResponse();
		response.setHeader("Content-Type", "text/html");

		byte[] data = FileReader.readFromFile(new File("htdocs/comments.html"));
		String body = new String(data);
		body = body.replace("_?", getComments());
		response.setBody(body);

		return response;
	}

	private void saveComment(HttpRequest httpRequest) {
		String name = httpRequest.getParameterString("name");
		String comment = httpRequest.getParameterString("comment");

		if(name != null && comment != null)
			commentDao.create(new Comment(name, comment));
	}

	private String getComments() {
		List<Comment> commentList = commentDao.getAll();
		StringBuilder stringBuilder = new StringBuilder();

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		for(Comment comment : commentList) {
			stringBuilder.append("<div>");
			stringBuilder.append(comment.getName());
			stringBuilder.append(" - ");
			stringBuilder.append(dateFormat.format(comment.getDate()));
			stringBuilder.append("<br>");
			stringBuilder.append(comment.getComment());
			stringBuilder.append("<div><br><hr>");
		}

		return stringBuilder.toString();
	}
}