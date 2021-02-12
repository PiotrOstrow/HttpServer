package org.example.db;

import java.util.List;

public interface CommentDAO {
	void create(Comment comment);
	List<Comment> getAll();
}
