package com.backend.service;

import com.backend.model.User;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    User saveUser(User user);

    Optional<User> getUserById(Long id);

    UserDetails getUserByEmail(String email);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(Long id);

}
