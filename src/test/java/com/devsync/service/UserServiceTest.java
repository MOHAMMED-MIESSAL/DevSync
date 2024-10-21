package com.devsync.service;

import com.devsync.entity.User;
import com.devsync.repository.implementations.UserRepositoryImpl;
import com.devsync.repository.interfaces.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class); // Mock du repository
        userService = new UserService(userRepository); // Injection du mock dans le service
    }

    @Test
    public void creteUserWithNoUsername() {
        // Arrange
        Boolean isManager = false;
        User user = new User();
        user.setEmail("email@gmail.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password");
        user.setIsManager(isManager);

        // Act
        userService.create(user);

        // Assert
        verify(userRepository, times(1)).create(user);


    }
//    public void create_shouldCallRepositoryCreate() {
//        // Arrange
//        User user = new User(); // Crée un objet utilisateur
//        user.setId(1L);
//        user.setUsername("testUser");
//        user.setEmail("test@devsync.com");
//
//        // Act
//        userService.create(user);
//
//        // Assert
//        verify(userRepository, times(1)).create(user); // Vérifie que la méthode create du repository a été appelée une fois
//    }

    @Test(expected = NullPointerException.class)
    public void create_nullUser() {
        // Act
        userService.create(null);
    }


}
