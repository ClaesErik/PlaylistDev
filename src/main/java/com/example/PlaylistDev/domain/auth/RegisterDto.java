package com.example.PlaylistDev.domain.auth;

import com.example.PlaylistDev.domain.user.UserRole;

public record RegisterDto(
        String login,
        String password,
        UserRole role
) {
}
