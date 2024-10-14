package com.devsync.repository.implementations;

import com.devsync.entity.Tag;
import com.devsync.repository.interfaces.TagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class TagRepositoryImpl implements TagRepository {
    private final EntityManager em;

    public TagRepositoryImpl() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");
        em = emf.createEntityManager();
    }

    @Override
    public void create(Tag tag) {
        em.getTransaction().begin();
        em.persist(tag);
        em.getTransaction().commit();
    }

    @Override
    public void update(Tag tag) {
        em.getTransaction().begin();
        em.merge(tag);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        Tag tag = em.find(Tag.class, id);
        if (tag != null) {
            em.remove(tag);
        }
        em.getTransaction().commit();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        Tag tag = em.find(Tag.class, id);
        return Optional.ofNullable(tag);
    }

    @Override
    public List<Tag> findAll() {
        return em.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
    }

    @Override
    public Optional<Tag> findByName(String name) {

            Tag tag = em.createQuery("SELECT t FROM Tag t WHERE t.name = :name", Tag.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.of(tag);
    }

}
