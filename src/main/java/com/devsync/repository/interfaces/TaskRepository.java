package com.devsync.repository.interfaces;

import com.devsync.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();

    void create(Task task);

    void update(Task task);

    void delete(Long tacheId);

    Optional<Task> findById(Long tacheId);
}
