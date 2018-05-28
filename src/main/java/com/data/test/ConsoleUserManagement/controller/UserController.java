package com.data.test.ConsoleUserManagement.controller;

import com.data.test.ConsoleUserManagement.dto.LoginCredentials;
import com.data.test.ConsoleUserManagement.dto.UserDto;
import com.data.test.ConsoleUserManagement.exception.UserAlreadyExistsException;
import com.data.test.ConsoleUserManagement.exception.WrongLoginCredentialsException;
import com.data.test.ConsoleUserManagement.exception.WrongUserInformationException;
import com.data.test.ConsoleUserManagement.service.UserService;
import com.data.test.ConsoleUserManagement.service.UserValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private final UserService userService;
    private final UserValidatorService userValidator;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class.getName());

    @Autowired
    public UserController(UserService userService, UserValidatorService userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }


    public void createNewUser(UserDto userDto) {
        try {
            checkUserConstraints(userDto);
            userService.addUserToDatabase(userDto);

        } catch (WrongUserInformationException | UserAlreadyExistsException e) {
            logger.info(e.getMessage());
        }
    }


    public Boolean loginUser(LoginCredentials loginCredentials) {
        try {
            userService.checkLoginCredentials(loginCredentials);
            return true;
        } catch (WrongLoginCredentialsException e) {
            logger.info(e.getMessage());
        }
        return false;
    }

    private void checkUserConstraints(UserDto userDto) throws WrongUserInformationException {
        userValidator.validateUser(userDto);
    }

    public void updateEmail(String username, String email) {
        try {
            checkNewEmailConstraints(email);
            userService.updateUserEmail(username, email);
        } catch (WrongUserInformationException e) {
            logger.info(e.getMessage());
        }
    }

    private void checkNewEmailConstraints(String email) throws WrongUserInformationException {
        userValidator.validateNewEmail(email);
    }

    public void updateTelephoneNumber(String username, String telephoneNumber) {
        try {
            checkNewTelephoneNumberConstraints(telephoneNumber);
            userService.updateUserTelephoneNumber(username, telephoneNumber);
        } catch (WrongUserInformationException e) {
            logger.info(e.getMessage());
        }
    }

    private void checkNewTelephoneNumberConstraints(String telephoneNumber) throws WrongUserInformationException {
        userValidator.validateNewTelephoneNumber(telephoneNumber);
    }
}
