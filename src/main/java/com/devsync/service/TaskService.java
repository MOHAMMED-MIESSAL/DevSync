package com.devsync.service;

import com.devsync.entity.Task;
import com.devsync.repository.implementations.TaskRepositoryImpl;
import com.devsync.repository.interfaces.TaskRepository;

import java.util.List;
import java.util.Optional;

public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService() {
        this.taskRepository = new TaskRepositoryImpl();
    }

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public void create(Task task) {
        taskRepository.create(task);
    }

    public void update(Task task) {
        taskRepository.update(task);
    }

    public void delete(Long id) {
        taskRepository.delete(id);
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

}
