package com.data.test.ConsoleUserManagement;

import com.data.test.ConsoleUserManagement.service.ConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Scanner;

@SpringBootApplication
public class ConsoleUserManagementApplication implements CommandLineRunner {

    @Autowired
    ConsoleService consoleService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {

        SpringApplication.run(ConsoleUserManagementApplication.class, args);

    }

    @Override
    public void run(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        /// can't use console, doesn't work with IntelIJ and eclipse, might add later
//		Console console = System.console();
        while (true) {
            System.out.println("Choose course of action");
            System.out.println("Available options: 1) register new user, 2) login to existing user, 3) quit");
            int option = scanner.nextInt();
            if (option == 1) {
                consoleService.registerRoutine(scanner);
            } else if (option == 2) {
                consoleService.loginRoutine(scanner);
            } else if (option == 3) {
                System.exit(0);
            } else {
                System.out.println("Wrong option, try again");
            }
        }
    }
}
