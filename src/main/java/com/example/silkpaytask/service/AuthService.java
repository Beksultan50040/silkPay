package com.example.silkpaytask.service;

import com.example.silkpaytask.config.UserAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserAuthProvider userAuthProvider;

    public void validateToken(String token) {
        userAuthProvider.validateToken(token);
    }


}
