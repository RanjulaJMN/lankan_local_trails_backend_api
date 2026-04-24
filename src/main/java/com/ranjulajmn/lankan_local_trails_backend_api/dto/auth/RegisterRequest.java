package com.ranjulajmn.lankan_local_trails_backend_api.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private String role;
}
