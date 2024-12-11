package com.damso.user.filter;

public interface CustomAuthenticationManager {
    Long authenticate(String email, String password);
}
