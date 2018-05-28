package com.data.test.ConsoleUserManagement.service;

import com.data.test.ConsoleUserManagement.ConsoleUserManagementApplicationTest;
import com.data.test.ConsoleUserManagement.dto.PasswordHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ConsoleUserManagementApplicationTest.class)
public class PasswordEncoderServiceTest {

    @Autowired
    PasswordEncoderService passwordEncoderService;

    @Test
    public void passwordEncodedTest(){
        ///given
        PasswordHolder password = new PasswordHolder("testPassword".toCharArray());

        ///when
        String encodedPassword = passwordEncoderService.encodePassword(password);

        ///then
        assertNotEquals(encodedPassword, String.valueOf(password.getCharArray()));
    }

    @Test
    public void passwordMatchingTest(){
        ///given
        PasswordHolder password = new PasswordHolder("testPassword".toCharArray());

        ///when
        String encodedPassword = passwordEncoderService.encodePassword(password);

        ///then
        assertTrue(passwordEncoderService.checkPassword(password, encodedPassword));
    }
}