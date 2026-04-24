package com.ranjulajmn.lankan_local_trails_backend_api.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
    private String username;
    private String password;
}
