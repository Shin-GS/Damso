package com.damso.common.auth;

public interface CustomAuthenticationManager {
    Long authenticateNotAdmin(String email, String password);

    Long authenticateAdmin(String email, String password);
}
