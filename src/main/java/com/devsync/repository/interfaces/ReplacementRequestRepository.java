package com.devsync.repository.interfaces;

import com.devsync.entity.ReplacementRequest;

import java.util.List;
import java.util.Optional;

public interface ReplacementRequestRepository {
    void save(ReplacementRequest request);
    Optional<ReplacementRequest> findById(Long id);
    List<ReplacementRequest> findAllPending();
    void update(ReplacementRequest request);
}

