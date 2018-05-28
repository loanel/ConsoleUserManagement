package com.data.test.ConsoleUserManagement.service;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordGenerator {
    private final String SMALL = "abcdefghijklmnopqrstuvwxyz";
    private final String BIG = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String DIGITS = "0123456789";
    private final String SPECIAL = "!.?@#$%^&+=";

    public char[] generatePassword() {
        String passwordBase = RandomStringUtils.random(12, 0, 0, true, true, null, new SecureRandom());
        String properPassword = configurePassword(passwordBase);
        return properPassword.toCharArray();
    }

    private String configurePassword(String passwordBase) {
        String properPassword = passwordBase;
        properPassword = configureSmallLetters(properPassword);
        properPassword = configureBigLetters(properPassword);
        properPassword = configureDigits(properPassword);
        properPassword = configureSpecial(properPassword);
        return properPassword;
    }

    private String configureSmallLetters(String password) {
        if (!checkSmallLetters(password)) {
            password = satisfyRequirement(password, SMALL);
        }
        return password;
    }

    private String configureBigLetters(String password) {
        if (!checkBigLetters(password)) {
            password = satisfyRequirement(password, BIG);
        }
        return password;
    }

    private String configureDigits(String password) {
        if (!checkDigit(password)) {
            password = satisfyRequirement(password, DIGITS);
        }
        return password;
    }

    private String configureSpecial(String password) {
        if (!checkSpecial(password)) {
            password = satisfyRequirement(password, SPECIAL);
        }
        return password;
    }

    private Boolean checkSmallLetters(String passwordBase) {
        String regex = "(?=.*[a-z]).+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(passwordBase);
        return matcher.matches();
    }

    private Boolean checkBigLetters(String passwordBase) {
        String regex = "(?=.*[A-Z]).+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(passwordBase);
        return matcher.matches();
    }

    private Boolean checkDigit(String passwordBase) {
        String regex = "(?=.*[0-9]).+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(passwordBase);
        return matcher.matches();
    }

    private Boolean checkSpecial(String passwordBase) {
        String regex = "(?=.*[!.?@#$%^&+=]).+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(passwordBase);
        return matcher.matches();
    }

    private String satisfyRequirement(String passwordBase, String charactersBase) {
        int placeToAdd = new Random().nextInt(passwordBase.length()) + 1;
        int letterToAdd = new Random().nextInt(charactersBase.length());
        return passwordBase.substring(0, placeToAdd) + charactersBase.charAt(letterToAdd) + passwordBase.substring(placeToAdd, passwordBase.length());
    }
}
