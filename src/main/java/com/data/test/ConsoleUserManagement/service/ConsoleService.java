package com.data.test.ConsoleUserManagement.service;

import com.data.test.ConsoleUserManagement.controller.ConsoleController;
import com.data.test.ConsoleUserManagement.dto.PasswordHolder;
import com.data.test.ConsoleUserManagement.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ConsoleService {
    @Autowired
    ConsoleController consoleController;


    public void registerRoutine(Scanner scanner) {
        System.out.println("Input username - required minimum 3 characters");
        String username = scanner.next();

        char[] password = getPassword(scanner);

        System.out.println("Input email");
        String email = scanner.next();

        System.out.println("Input telephone number");
        String telephoneNumber = scanner.next();

        UserDto userDto = new UserDto(username, new PasswordHolder(password), email, telephoneNumber);
        if (consoleController.addUserToDatabase(userDto)) {
            System.out.println("Successfully registered new user");
        } else {
            System.out.println("Failed to register new user, try again");
        }
    }

    public char[] getPassword(Scanner scanner) {
        System.out.println("Choose action: 1) generate password, 2) input password");
        int option = scanner.nextInt();
        if (option == 1) {
            return generatePassword();
        } else {
            System.out.println("Input password - required minimum 8 characters, one uppercase, one lowercase, one digit, one special sign");
            return scanner.next().toCharArray();
        }
    }

    private char[] generatePassword() {
        String LOWER = "abcdefghijklmnopqrstuvwxyz";
        String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String DIGITS = "0123456789";
        String PUNCTUATION = "!.?@#$%^&+=";

        ///temporary
        System.out.println("Your password is AaBc!?23Kjl");
        return "AaBc!?23Kjl".toCharArray();
    }

    public void loginRoutine(Scanner scanner) {
        System.out.println("Input username");
        String username = scanner.next();

        System.out.println("Input password");
        PasswordHolder password = new PasswordHolder(scanner.next().toCharArray());

        if (consoleController.loginUser(username, password)) {
            loggedInRoutine(scanner, username);
        } else {
            System.out.println("Failed to login, try again");
        }
    }

    private void loggedInRoutine(Scanner scanner, String username) {
        System.out.println("Successfully logged in as user" + username + ", choose action");
        while (true) {
            System.out.println("Available options: 1) change email, 2) change telephone number 3) logout");
            int option = scanner.nextInt();
            if (option == 1) {
                changeEmailRoutine(username, scanner);
            } else if (option == 2) {
                changeTelephoneRoutine(username, scanner);
            } else if (option == 3) {
                break;
            } else {
                System.out.println("Wrong option, try again");
            }
        }
    }

    private void changeEmailRoutine(String username, Scanner scanner) {
        System.out.println("Input new email");
        String email = scanner.next();
        if (!validateEmail(email)) {
            System.out.println("Wrong email, please input a proper email address");
        } else {
            if (consoleController.updateEmail(username, email)) {
                System.out.println("The email for user " + username + " has updated");
            } else {
                System.out.println("Failed to update email, try again");
            }
        }
    }

    private Boolean validateEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void changeTelephoneRoutine(String username, Scanner scanner) {
        System.out.println("Input new telephone number");
        String telephoneNumber = scanner.next();
        if (!validateTelephoneNumber(telephoneNumber)) {
            System.out.println("Wrong telephone number, please only input digits");
        } else {
            if (consoleController.updateTelephoneNumber(username, telephoneNumber)) {
                System.out.println("The telephone number for user " + username + " has updated");
            } else {
                System.out.println("Failed to update telephone number, try again");
            }
        }
    }

    private Boolean validateTelephoneNumber(String telephoneNumber) {
        String regex = "[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(telephoneNumber);
        return matcher.matches();
    }
}
