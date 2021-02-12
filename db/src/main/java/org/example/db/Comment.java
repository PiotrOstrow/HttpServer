package org.example.db;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "Comments", schema = "dbo")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private String comment;
	private Timestamp date;

	public Comment() {

	}

	public Comment(String name, String comment) {
		this.name = name;
		this.comment = comment;
		this.date = Timestamp.from(Instant.now());
	}

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
