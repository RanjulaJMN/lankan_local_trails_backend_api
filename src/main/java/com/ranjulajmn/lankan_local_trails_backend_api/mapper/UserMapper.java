package com.ranjulajmn.lankan_local_trails_backend_api.mapper;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.UserResponseDTO;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserResponseDTO toDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }

    public List<UserResponseDTO> toDtoList(List<User> users) {
        if (users == null) {
            return null;
        }

        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
