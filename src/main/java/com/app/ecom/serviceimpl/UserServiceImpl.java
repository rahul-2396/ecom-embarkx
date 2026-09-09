package com.app.ecom.serviceimpl;

import com.app.ecom.entity.User;
import com.app.ecom.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final List<User> userList = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public List<User> fetchAllUsers() {
        return userList;
    }

    @Override
    public Optional<User> userById(Long id) {
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public void addUser(User user) {
        user.setId(nextId++);
        userList.add(user);
    }

    @Override
    public boolean updateUser(Long id, User updatedUser) {
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    return true;
                }).orElse(false);
    }
}

