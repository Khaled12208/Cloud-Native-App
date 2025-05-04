package com.example.authservice.service;

import com.example.authservice.model.User;

public interface UserService {
    User createUser(User user);
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 