package com.data.test.ConsoleUserManagement.service;

import com.data.test.ConsoleUserManagement.ConsoleUserManagementApplicationTest;
import com.data.test.ConsoleUserManagement.dto.PasswordHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ActiveProfiles(value = "test")
@SpringBootTest(classes = ConsoleUserManagementApplicationTest.class)
@ComponentScan(basePackages = {
        "com.data.test.ConsoleUserManagement.controller",
        "com.data.test.ConsoleUserManagement.service",
        "com.data.test.ConsoleUserManagement.repository",
})
public class PasswordEncoderServiceTest {

    @Autowired
    PasswordEncoderService passwordEncoderService;

    @Test
    public void passwordEncodedTest() {
        ///given
        PasswordHolder password = new PasswordHolder("testPassword".toCharArray());

        ///when
        String encodedPassword = passwordEncoderService.encodePassword(password);

        ///then
        assertNotEquals(encodedPassword, String.valueOf(password.getCharArray()));
    }

    @Test
    public void passwordMatchingTest() {
        ///given
        PasswordHolder password = new PasswordHolder("testPassword".toCharArray());

        ///when
        String encodedPassword = passwordEncoderService.encodePassword(password);

        ///then
        assertTrue(passwordEncoderService.checkPassword(password, encodedPassword));
    }
}