package org.example.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ImageDaoJPAImpl implements ImageDAO {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Unit1");

    @Override
    public void create(Image image) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(image);
        em.getTransaction().commit();
    }

    @Override
    public List<Image> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Image> list = em.createQuery("from Image").getResultList();
        em.getTransaction().commit();
        return list;
    }

}
