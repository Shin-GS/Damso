package com.damso.auth.service;

public interface CustomAuthenticationManager {
    Long authenticate(String email, String password);
}
