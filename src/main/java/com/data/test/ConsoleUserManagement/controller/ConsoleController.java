package com.data.test.ConsoleUserManagement.controller;

import com.data.test.ConsoleUserManagement.dto.PasswordHolder;
import com.data.test.ConsoleUserManagement.dto.UserDto;
import com.data.test.ConsoleUserManagement.model.User;
import com.data.test.ConsoleUserManagement.repository.UserRepository;
import com.data.test.ConsoleUserManagement.service.UserValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class ConsoleController {

    private final UserRepository userRepository;
    private final UserValidatorService userValidator;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ConsoleController(UserRepository userRepository, UserValidatorService userValidator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    public Boolean addUserToDatabase(UserDto user) {
        if (!checkUserConstraints(user)) {
            return false;
        }
        User userToAdd = new User();
        userToAdd.setUsername(user.getUsername());
        System.out.println(user.getPassword().getCharArray());
        userToAdd.setPassword(encodePassword(user.getPassword()));
        userToAdd.setEmail(user.getEmail());
        userToAdd.setTelephoneNumber(user.getTelephoneNumber());
        userRepository.save(userToAdd);

        return true;
    }

    private String encodePassword(CharSequence normalPassword) {
        return passwordEncoder.encode(normalPassword);
    }

    public Boolean loginUser(String username, PasswordHolder password) {
        if (!userRepository.existsByUsername(username)) {
            System.out.println("No such user exists in database");
            return false;
        }
        if (!checkPasswordCorrect(username, password)) {
            System.out.println("Wrong password, try again");
            return false;
        }
        return true;
    }

    private Boolean checkPasswordCorrect(String username, PasswordHolder password) {
        return passwordEncoder.matches(password, userRepository.findByUsername(username).getPassword());
    }

    public Boolean updateEmail(String username, String email) {
        User userToUpdate = userRepository.findByUsername(username);
        userToUpdate.setEmail(email);
        userRepository.save(userToUpdate);
        return true;
    }

    public Boolean updateTelephoneNumber(String username, String telephoneNumber) {
        User userToUpdate = userRepository.findByUsername(username);
        userToUpdate.setTelephoneNumber(telephoneNumber);
        userRepository.save(userToUpdate);
        return true;
    }

    private Boolean checkUserConstraints(UserDto user) {
        if (!userValidator.validateUser(user)) {
            return false;
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            System.out.println("User with this username already exists");
            return false;
        }

        return true;
    }


}
