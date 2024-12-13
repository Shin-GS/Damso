package com.damso.user.security;

public interface CustomAuthenticationManager {
    Long authenticate(String email, String password);
}
