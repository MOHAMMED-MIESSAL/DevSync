package com.devsync.repository.implementations;

import com.devsync.entity.Task;
import com.devsync.repository.interfaces.TaskRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class TaskRepositoryImpl implements TaskRepository {

    private final EntityManager em;

    public TaskRepositoryImpl() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");
        em = emf.createEntityManager();
    }

    @Override
    public List<Task> findAll() {
        return em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
    }

    @Override
    public void create(Task task) {
        try {
            em.getTransaction().begin();
            em.persist(task);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void update(Task task) {
        try {
            em.getTransaction().begin();
            em.merge(task);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void delete(Long tacheId) {
        try {
            em.getTransaction().begin();
            Task task = em.find(Task.class, tacheId);
            if (task != null) {
                em.remove(task);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public Optional<Task> findById(Long tacheId) {
        Task task = em.find(Task.class, tacheId);
        return Optional.ofNullable(task);
    }
}
