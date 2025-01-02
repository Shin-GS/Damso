package com.damso.auth.service;

public interface CustomAuthenticationManager {
    Long authenticateNotAdmin(String email, String password);

    Long authenticateAdmin(String email, String password);
}
