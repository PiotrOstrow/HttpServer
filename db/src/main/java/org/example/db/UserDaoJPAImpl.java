package org.example.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UserDaoJPAImpl implements UserDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Unit1");

    @Override
    public User create(User u) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        return u;
    }

    @Override
    public List<User> getAll() {
        List<User> list;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        list = em.createQuery("from User", User.class).getResultList();
        em.getTransaction().commit();
        return list;
    }
}
