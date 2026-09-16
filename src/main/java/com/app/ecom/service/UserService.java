package com.app.ecom.service;

import com.app.ecom.dto.UserRequestDTO;
import com.app.ecom.dto.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponseDTO> fetchAllUsers();

    Optional<UserResponseDTO> userById(Long id);

    void addUser(UserRequestDTO userRequestDTO);

    boolean updateUser(Long id, UserRequestDTO updatedUser);
}