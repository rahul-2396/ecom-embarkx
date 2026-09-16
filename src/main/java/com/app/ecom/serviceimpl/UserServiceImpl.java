package com.app.ecom.serviceimpl;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequestDTO;
import com.app.ecom.dto.UserResponseDTO;
import com.app.ecom.entity.Address;
import com.app.ecom.entity.User;
import com.app.ecom.repository.UserRepository;
import com.app.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserResponseDTO> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponseDTO> userById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponseDTO);
    }

    @Override
    public void addUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        mapToUser(user, userRequestDTO);
        userRepository.save(user);
    }

    @Override
    public boolean updateUser(Long id, UserRequestDTO updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    mapToUser(existingUser, updatedUser);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private void mapToUser(User user, UserRequestDTO userRequestDTO) {
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhone(userRequestDTO.getPhone());

        if (userRequestDTO.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequestDTO.getAddress().getStreet());
            address.setCity(userRequestDTO.getAddress().getCity());
            address.setState(userRequestDTO.getAddress().getState());
            address.setCountry(userRequestDTO.getAddress().getCountry());
            address.setZipcode(userRequestDTO.getAddress().getZipcode());
            user.setAddress(address);
        }
    }

    private UserResponseDTO mapToUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(String.valueOf(user.getId()));
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhone(user.getPhone());
        userResponseDTO.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            userResponseDTO.setAddressDTO(addressDTO);
        }
        return userResponseDTO;
    }
}