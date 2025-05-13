package com.example.demo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to create a new user


    // Endpoint to get all users
    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("id-by-email")
    public ResponseEntity<Long> getUserByEmail(@RequestParam String email) {
        Long id = userService.findIdByEmail(email);
        if (id == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(id);
    }

    // Endpoint to get a specific user by ID
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }
}
