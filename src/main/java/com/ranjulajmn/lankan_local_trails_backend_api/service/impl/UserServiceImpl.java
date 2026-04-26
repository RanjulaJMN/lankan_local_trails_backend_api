package com.ranjulajmn.lankan_local_trails_backend_api.service.impl;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.UserResponseDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.Role;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.User;
import com.ranjulajmn.lankan_local_trails_backend_api.mapper.UserMapper;
import com.ranjulajmn.lankan_local_trails_backend_api.repository.UserRepository;
import com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id: " + id));
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with username: " + username));
        return userMapper.toDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id: " + id));

        // Only allow deletion of ROLE_USER
        if (userToDelete.getRole() != Role.ROLE_USER) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Cannot delete users with role: " + userToDelete.getRole() + ". Only ROLE_USER can be deleted.");
        }

        // Prevent admin from deleting themselves
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Current user not found"));

        if (currentUser.getId().equals(id) && currentUser.getRole() == Role.ROLE_ADMIN) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Admin cannot delete their own account through this endpoint.");
        }

        userRepository.deleteById(id);
    }
}
