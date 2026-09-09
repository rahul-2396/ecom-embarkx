package com.app.ecom.service;

import com.app.ecom.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> fetchAllUsers();

    Optional<User> userById(Long id);

    void addUser(User user);

    boolean updateUser(Long id, User updatedUser);
}


