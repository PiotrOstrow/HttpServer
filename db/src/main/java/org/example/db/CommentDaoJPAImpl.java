package org.example.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class CommentDaoJPAImpl implements CommentDAO{

	private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Unit1");

	@Override
	public void create(Comment comment) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(comment);
		em.getTransaction().commit();
	}

	@Override
	public List<Comment> getAll() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Comment> list = em.createQuery("from Comment").getResultList();
		em.getTransaction().commit();
		return list;
	}
}
