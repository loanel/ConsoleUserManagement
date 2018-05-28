package com.data.test.ConsoleUserManagement.service;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class PasswordGeneratorTest {

    private static PasswordGenerator passwordGenerator;

    @BeforeClass
    public static void preStart() {
        passwordGenerator = new PasswordGenerator();
    }


    @Test
    public void passwordContainsSmall() {
        ///given
        String regex = "(?=.*[a-z]).+";
        Pattern pattern = Pattern.compile(regex);

        ///when
        String password = String.valueOf(passwordGenerator.generatePassword());
        Matcher matcher = pattern.matcher(password);
        System.out.println(password);

        ///then
        assertTrue(matcher.matches());
    }

    @Test
    public void passwordContainsBig() {
        ///given
        String regex = "(?=.*[A-Z]).+";
        Pattern pattern = Pattern.compile(regex);

        ///when
        String password = String.valueOf(passwordGenerator.generatePassword());
        Matcher matcher = pattern.matcher(password);
        System.out.println(password);

        ///then
        assertTrue(matcher.matches());
    }

    @Test
    public void passwordContainsDigit() {
        ///given
        String regex = "(?=.*[0-9]).+";
        Pattern pattern = Pattern.compile(regex);

        ///when
        String password = String.valueOf(passwordGenerator.generatePassword());
        Matcher matcher = pattern.matcher(password);
        System.out.println(password);

        ///then
        assertTrue(matcher.matches());
    }

    @Test
    public void passwordContainsSpecial() {
        ///given
        String regex = "(?=.*[!.?@#$%^&+=]).+";
        Pattern pattern = Pattern.compile(regex);

        ///when
        String password = String.valueOf(passwordGenerator.generatePassword());
        Matcher matcher = pattern.matcher(password);
        System.out.println(password);

        ///then
        assertTrue(matcher.matches());
    }
}