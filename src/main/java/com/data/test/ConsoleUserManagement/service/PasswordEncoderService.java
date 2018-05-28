package com.data.test.ConsoleUserManagement.service;

import com.data.test.ConsoleUserManagement.dto.PasswordHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordEncoderService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    String encodePassword(PasswordHolder password) {
        return passwordEncoder.encode(password);
    }

    Boolean checkPassword(PasswordHolder password, String databasePassword) {
        return passwordEncoder.matches(password, databasePassword);
    }

}
