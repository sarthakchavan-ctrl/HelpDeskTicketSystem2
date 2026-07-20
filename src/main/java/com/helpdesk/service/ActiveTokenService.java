package com.helpdesk.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service


public class ActiveTokenService {

    // Stores logged-in users and their tokens
    private final Map<String, String> activeTokens = new ConcurrentHashMap<>();

    // Check if user is already logged in
    public boolean isLoggedIn(String email) {
        return activeTokens.containsKey(email);
    }

    // Save token after successful login
    public void saveToken(String email, String token) {
        activeTokens.put(email, token);
    }

    // Get token of logged-in user
    public String getToken(String email) {
        return activeTokens.get(email);
    }

    // Remove user on logout
    public void logout(String email) {
        activeTokens.remove(email);
    }

    // Check if a token is active
    public boolean isTokenActive(String token) {
        return activeTokens.containsValue(token);
    }
}