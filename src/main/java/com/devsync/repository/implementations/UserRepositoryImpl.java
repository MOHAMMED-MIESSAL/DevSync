package com.devsync.repository.implementations;

import com.devsync.entity.User;
import com.devsync.repository.interfaces.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private final EntityManager em;

    public UserRepositoryImpl() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");
        em = emf.createEntityManager();
    }


    @Override
    public void create(User user) {
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void delete(Long userId) {
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, userId);
            if (user != null) {
                em.remove(user);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public Optional<User> findById(Long userId) {
        User user = em.find(User.class, userId);
        return Optional.ofNullable(user);
    }

    @Override
    public void update(User user) {
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }
}
