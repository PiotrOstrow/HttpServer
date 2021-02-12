package org.example.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


public class CountryDAOWithJPAImpl implements CountryDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");

    @Override
    public void create(Country c) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
    }

    @Override
    public List<Country> getAll() {
        List<Country> list;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        list = em.createQuery("from Country", Country.class).getResultList();
        em.getTransaction().commit();
        return list;
    }
}
