

package com.backend.controller;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    // @Autowired
    // private UserService userService;

    @GetMapping
    public List<String> getAllUsers() {
        // create a list of fake users
        List<String> fakeUsers = Arrays.asList("John", "Jane", "Mikeuhjb");



        return fakeUsers;
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<User> getUserById(@PathVariable Long id) {
    //     User user = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
    //     return ResponseEntity.ok(user);
    // }

    // @PostMapping
    // public User createUser(@RequestBody User user) {
    //     return userService.createUser(user);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
    //     User updatedUser = userService.updateUser(id, userDetails);
    //     return ResponseEntity.ok(updatedUser);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    //     userService.deleteUser(id);
    //     return ResponseEntity.noContent().build();
    // }
}
