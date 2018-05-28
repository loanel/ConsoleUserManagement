package com.data.test.ConsoleUserManagement.parser;

import com.data.test.ConsoleUserManagement.dto.LoginCredentials;
import com.data.test.ConsoleUserManagement.dto.PasswordHolder;
import com.data.test.ConsoleUserManagement.dto.UserDto;
import com.data.test.ConsoleUserManagement.service.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ConsoleInputParser {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleInputParser.class.getName());
    private final Scanner scanner;

    @Autowired
    PasswordGenerator passwordGenerator;

    public ConsoleInputParser() {
        this.scanner = new Scanner(System.in);
    }

    public UserDto getNewUserInformation() {
        String username = getUsername();
        char[] password = getPassword();
        String email = getEmail();
        String telephoneNumber = getTelephoneNumber();

        return new UserDto(username, new PasswordHolder(password), email, telephoneNumber);
    }

    private String getTelephoneNumber() {
        logger.info("Input telephone number");
        return scanner.next();
    }

    private String getUsername() {
        logger.info("Input username - required minimum 3 characters");
        return scanner.next();
    }

    private String getEmail() {
        logger.info("Input email");
        return scanner.next();
    }

    private char[] getPassword() {
        logger.info("Choose action: 1) generate password, 2) input password");
        int option = scanner.nextInt();
        if (option == 1) {
            return generatePassword();
        } else {
            logger.info("Input password - required minimum 8 characters, one uppercase, one lowercase, one digit, one special sign");
            return scanner.next().toCharArray();
        }
    }

    private char[] generatePassword() {
        char[] generatedPassword = passwordGenerator.generatePassword();
        logger.info("Your generated password is: " + String.valueOf(generatedPassword) + " , in characters -> " + Arrays.toString(generatedPassword));
        return generatedPassword;
    }

    public LoginCredentials getLoginCredentials() {
        logger.info("Input username");
        String username = scanner.next();

        logger.info("Input password");
        PasswordHolder password = new PasswordHolder(scanner.next().toCharArray());

        return new LoginCredentials(username, password);
    }


    public String getNewUserEmail() {
        logger.info("Input new email");
        String email = scanner.next();
        while (!validateEmail(email)) {
            logger.info("Wrong email, please input a proper email address");
            email = scanner.next();
        }
        return email;
    }

    private Boolean validateEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public String getNewTelephoneNumber() {
        logger.info("Input new telephone number");
        String telephoneNumber = scanner.next();
        while (!validateTelephoneNumber(telephoneNumber)) {
            logger.info("Wrong telephone number, please only input digits");
            telephoneNumber = scanner.next();
        }
        return telephoneNumber;
    }

    private Boolean validateTelephoneNumber(String telephoneNumber) {
        String regex = "[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(telephoneNumber);
        return matcher.matches();
    }
}
