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


    public void createNewUser(UserDto userDto) {
        try {
            checkUserConstraints(userDto);
            consoleService.addUserToDatabase(userDto);

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

    private void checkUserConstraints(UserDto userDto) throws WrongUserInformationException {
        userValidator.validateUser(userDto);
    }

    public void updateEmail(String username, String email) {
        try {
            checkNewEmailConstraints(email);
            consoleService.updateUserEmail(username, email);
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
            consoleService.updateUserTelephoneNumber(username, telephoneNumber);
        } catch (WrongUserInformationException e) {
            logger.info(e.getMessage());
        }
    }

    private void checkNewTelephoneNumberConstraints(String telephoneNumber) throws WrongUserInformationException {
        userValidator.validateNewTelephoneNumber(telephoneNumber);
    }
}
