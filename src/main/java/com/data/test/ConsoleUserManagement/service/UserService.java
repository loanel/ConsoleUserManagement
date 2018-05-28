package com.data.test.ConsoleUserManagement.service;

import com.data.test.ConsoleUserManagement.dto.LoginCredentials;
import com.data.test.ConsoleUserManagement.dto.UserDto;
import com.data.test.ConsoleUserManagement.exception.UserAlreadyExistsException;
import com.data.test.ConsoleUserManagement.exception.WrongLoginCredentialsException;
import com.data.test.ConsoleUserManagement.model.User;
import com.data.test.ConsoleUserManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoderService passwordEncoderService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoderService passwordEncoderService) {
        this.userRepository = userRepository;
        this.passwordEncoderService = passwordEncoderService;
    }

    public void addUserToDatabase(UserDto userDto) throws UserAlreadyExistsException {
        if (userExistsInDatabase(userDto.getUsername()))
            throw new UserAlreadyExistsException("User already exists in database, choose another username");
        userRepository.save(createDatabaseUser(userDto));
    }

    private Boolean userExistsInDatabase(String username) {
        return userRepository.existsByUsername(username);
    }

    private User createDatabaseUser(UserDto userDto) {
        User userToAdd = new User();
        userToAdd.setUsername(userDto.getUsername());
        userToAdd.setPassword(passwordEncoderService.encodePassword(userDto.getPassword()));
        userToAdd.setEmail(userDto.getEmail());
        userToAdd.setTelephoneNumber(userDto.getTelephoneNumber());
        return userToAdd;
    }

    public void checkLoginCredentials(LoginCredentials loginCredentials) throws WrongLoginCredentialsException {
        if (!userRepository.existsByUsername(loginCredentials.getUsername())) {
            throw new WrongLoginCredentialsException("Wrong username provided, try again");
        }
        if (!passwordEncoderService.checkPassword(loginCredentials.getPassword(), getUserPassword(loginCredentials.getUsername()))) {
            throw new WrongLoginCredentialsException("Wrong password provided, try again");
        }
    }

    private String getUserPassword(String username) {
        return userRepository.findByUsername(username).getPassword();
    }

    public void updateUserEmail(String username, String email) {
        User userToChange = userRepository.findByUsername(username);
        userToChange.setEmail(email);
        userRepository.save(userToChange);
    }

    public void updateUserTelephoneNumber(String username, String telephoneNumber) {
        User userToChange = userRepository.findByUsername(username);
        userToChange.setTelephoneNumber(telephoneNumber);
        userRepository.save(userToChange);
    }
}
