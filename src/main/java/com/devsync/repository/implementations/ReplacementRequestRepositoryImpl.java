package com.devsync.repository.implementations;

import com.devsync.entity.ReplacementRequest;
import com.devsync.repository.interfaces.ReplacementRequestRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class ReplacementRequestRepositoryImpl implements ReplacementRequestRepository {

    private final EntityManager entityManager;

    public ReplacementRequestRepositoryImpl() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");
        entityManager = emf.createEntityManager();
    }

    @Override
    public void save(ReplacementRequest request) {
        entityManager.getTransaction().begin();
        entityManager.persist(request);
        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<ReplacementRequest> findById(Long id) {
        ReplacementRequest request = entityManager.find(ReplacementRequest.class, id);
        return Optional.ofNullable(request);
    }

    @Override
    public List<ReplacementRequest> findAllPending() {
        String query = "SELECT r FROM ReplacementRequest r WHERE r.status = 'PENDING'";
        return entityManager.createQuery(query, ReplacementRequest.class).getResultList();
    }

    @Override
    public void update(ReplacementRequest request) {
        entityManager.getTransaction().begin();
        entityManager.merge(request);
        entityManager.getTransaction().commit();
    }
}

