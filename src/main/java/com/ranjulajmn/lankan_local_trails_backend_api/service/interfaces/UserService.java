package com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO getUserByUsername(String username);
    void deleteUser(Long id);
}
