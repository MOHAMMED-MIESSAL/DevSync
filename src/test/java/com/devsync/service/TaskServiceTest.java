package com.devsync.service;



import com.devsync.entity.Tag;
import com.devsync.entity.Task;
import com.devsync.entity.User;
import com.devsync.enums.Status;
import com.devsync.exceptions.TaskCreationException;
import com.devsync.exceptions.ValidationException;
import com.devsync.repository.interfaces.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    private TaskService taskService;
    private TaskRepository taskRepository;

    @Before
    public void setUp() {
        taskRepository = Mockito.mock(TaskRepository.class);
        taskService = new TaskService();
    }

    @Test(expected = ValidationException.class)
    public void create_shouldThrowValidationException_whenUserNotManagerAndTaskNotAssignedToCurrentUser() {
        // Arrange
        User currentUser = new User();
        currentUser.setIsManager(false);
        currentUser.setId(1L);

        User assignedUser = new User();
        assignedUser.setId(2L);

        Task task = new Task();
        task.setUserAffected(assignedUser);

        // Act
        taskService.create(task, currentUser);
    }

    @Test(expected = TaskCreationException.class)
    public void create_shouldThrowTaskCreationException_whenDueDateIsMoreThan3DaysAhead() {
        // Arrange
        User currentUser = new User();
        currentUser.setIsManager(true);

        Task task = new Task();
        task.setUserAffected(currentUser);
        task.setDateEcheance(LocalDate.now().plusDays(4).atStartOfDay());

        // Act
        taskService.create(task, currentUser);
    }

    @Test(expected = TaskCreationException.class)
    public void create_shouldThrowTaskCreationException_whenCreationDateIsInThePast() {
        // Arrange
        User currentUser = new User();
        currentUser.setIsManager(true);

        Task task = new Task();
        task.setUserAffected(currentUser);
        task.setDateCreation(LocalDate.now().minusDays(1).atStartOfDay());

        // Act
        taskService.create(task, currentUser);
    }

    @Test(expected = ValidationException.class)
    public void create_shouldThrowValidationException_whenTaskNameIsEmpty() {
        // Arrange
        User currentUser = new User();
        currentUser.setIsManager(true);

        Task task = new Task();
        task.setUserAffected(currentUser);
        task.setName("");

        // Act
        taskService.create(task, currentUser);
    }

    @Test(expected = ValidationException.class)
    public void create_shouldThrowValidationException_whenDescriptionIsEmpty() {
        // Arrange
        User currentUser = new User();
        currentUser.setIsManager(true);

        Task task = new Task();
        task.setUserAffected(currentUser);
        task.setDescription("");

        // Act
        taskService.create(task, currentUser);
    }

    @Test(expected = ValidationException.class)
    public void create_shouldThrowValidationException_whenStatusIsNull() {
        // Arrange
        User currentUser = new User();
        currentUser.setIsManager(true);

        Task task = new Task();
        task.setUserAffected(currentUser);
        task.setStatus(null);

        // Act
        taskService.create(task, currentUser);
    }

    @Test(expected = ValidationException.class)
    public void create_shouldThrowValidationException_whenUserAffectedIsNull() {
        // Arrange
        User currentUser = new User();
        currentUser.setIsManager(true);

        Task task = new Task();
        task.setUserAffected(null);

        // Act
        taskService.create(task, currentUser);
    }

    @Test(expected = ValidationException.class)
    public void create_shouldThrowValidationException_whenTaskHasLessThanTwoTags() {
        // Arrange
        User currentUser = new User();
        currentUser.setIsManager(true);

        Task task = new Task();
        task.setUserAffected(currentUser);
        task.setTags(new HashSet<>(Arrays.asList(new Tag("Tag1")))); // only 1 tag

        // Act
        taskService.create(task, currentUser);
    }

    @Test
    public void create_shouldSetCreationDate_whenTaskCreationDateIsNull() {
        // Arrange
        User currentUser = new User();
        currentUser.setIsManager(true);

        Task task = new Task();
        task.setUserAffected(currentUser);
        task.setTags(new HashSet<>(Arrays.asList(new Tag("Tag1"), new Tag("Tag2")))); // valid number of tags
        task.setName("Task Title");
        task.setDescription("Task Description");
        task.setStatus(Status.NOT_STARTED);
        task.setDateCreation(null);

        // Act
        taskService.create(task, currentUser);

        // Assert
        assertNotNull(task.getDateCreation());
        verify(taskRepository, times(1)).create(task); // ensure the taskRepository.create is called
    }
}
