package com.backend.service.impl;

import com.backend.model.User;
import com.backend.model.DTO.UserRegistrationDTO;
import com.backend.model.Enum.RoleName;
import com.backend.repository.RoleRepository;
import com.backend.repository.UserRepository;
import com.backend.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.backend.model.Role;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void registerUser(UserRegistrationDTO user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setSurname(user.getSurname());
        newUser.setPassword(user.getPassword());
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        newUser.setPassword(encodedPassword);

        // Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
        // .orElseThrow(() -> new RuntimeException("Error: Ruolo non trovato."));

        Set<Role> roles = new HashSet<>();
        Role userRole = new Role();
        userRole.setName(RoleName.ROLE_USER);
        roles.add(userRole);
        newUser.setRoles(roles);

        userRepository.save(newUser);
    }

}
