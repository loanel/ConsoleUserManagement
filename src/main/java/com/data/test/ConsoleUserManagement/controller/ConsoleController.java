package com.data.test.ConsoleUserManagement.controller;

import com.data.test.ConsoleUserManagement.dto.LoginCredentials;
import com.data.test.ConsoleUserManagement.dto.UserDto;
import com.data.test.ConsoleUserManagement.exception.UserAlreadyExistsException;
import com.data.test.ConsoleUserManagement.exception.WrongLoginCredentialsException;
import com.data.test.ConsoleUserManagement.exception.WrongUserInformationException;
import com.data.test.ConsoleUserManagement.service.ConsoleService;
import com.data.test.ConsoleUserManagement.service.UserValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ConsoleController {


    private final ConsoleService consoleService;
    private final UserValidatorService userValidator;

    private static final Logger logger = LoggerFactory.getLogger(ConsoleController.class.getName());

    @Autowired
    public ConsoleController(ConsoleService consoleService, UserValidatorService userValidator) {
        this.consoleService = consoleService;
        this.userValidator = userValidator;
    }


    public void createNewUser(UserDto user) {
        try {
            if (!checkUserConstraints(user)) {
                throw new WrongUserInformationException("Failed to login, wrong user information provided");
            }
            consoleService.addUserToDatabase(user);

        } catch (WrongUserInformationException | UserAlreadyExistsException e) {
            logger.info(e.getMessage());
        }
    }

    public Boolean loginUser(LoginCredentials loginCredentials) {
        try {
            consoleService.checkLoginCredentials(loginCredentials);
            return true;
        } catch (WrongLoginCredentialsException e) {
            logger.info(e.getMessage());
        }
        return false;
    }

    private Boolean checkUserConstraints(UserDto userDto) {
        return userValidator.validateUser(userDto);
    }

    public void updateEmail(String username, String email) {
        consoleService.updateUserEmail(username, email);
    }

    public void updateTelephoneNumber(String username, String telephoneNumber) {
        consoleService.updateUserTelephoneNumber(username, telephoneNumber);
    }
}
