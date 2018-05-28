package com.data.test.ConsoleUserManagement.controller;

import com.data.test.ConsoleUserManagement.ConsoleUserManagementApplicationTest;
import com.data.test.ConsoleUserManagement.controller.ConsoleController;
import com.data.test.ConsoleUserManagement.dto.LoginCredentials;
import com.data.test.ConsoleUserManagement.dto.PasswordHolder;
import com.data.test.ConsoleUserManagement.dto.UserDto;
import com.data.test.ConsoleUserManagement.exception.WrongUserInformationException;
import com.data.test.ConsoleUserManagement.repository.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles(value = "test")
@SpringBootTest(classes = ConsoleUserManagementApplicationTest.class)
@ComponentScan(basePackages = {
        "com.data.test.ConsoleUserManagement.controller",
        "com.data.test.ConsoleUserManagement.service",
        "com.data.test.ConsoleUserManagement.repository",
})
public class ConsoleControllerTest {

    @Autowired
    ConsoleController consoleController;

    @Autowired
    UserRepository userRepository;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setupStreams(){
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void createNewUserTest() {
        ///given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", "123456789");

        ///when
        consoleController.createNewUser(userDto);

        ///then
        userRepository.existsByUsername("testUsername");
    }

    @Test
    public void createNewUserValidationFailTest(){
        ///given
        UserDto userDto = new UserDto("aa", new PasswordHolder("aa".toCharArray()), "taa", "aa");

        ///when
        consoleController.createNewUser(userDto);

        ///then
        //ValidatorService threw an exception, caught here
        assertFalse(userRepository.existsByUsername("aa"));
        assertTrue(outContent.toString().contains("Unable to create new user, wrong user information provided"));
    }

    @Test
    public void loginUserTest(){
        ///given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", "123456789");
        LoginCredentials loginCredentials = new LoginCredentials("testUsername", new PasswordHolder("testpas!AA1".toCharArray()));

        ///when
        consoleController.createNewUser(userDto);
        Boolean loginPassed = consoleController.loginUser(loginCredentials);
        ///then

        assertTrue(loginPassed);
    }

    @Test
    public void loginUserWrongUsernameTest(){
        ///given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", "123456789");
        LoginCredentials loginCredentials = new LoginCredentials("wrongTestUsername", new PasswordHolder("testpas!AA1".toCharArray()));

        ///when
        consoleController.createNewUser(userDto);
        Boolean loginPassed = consoleController.loginUser(loginCredentials);
        ///then

        assertFalse(loginPassed);
        assertTrue(outContent.toString().contains("Wrong username provided, try again"));
    }

    @Test
    public void loginUserWrongPasswordTest(){
        ///given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", "123456789");
        LoginCredentials loginCredentials = new LoginCredentials("testUsername", new PasswordHolder("aaaaaaA!!a1".toCharArray()));

        ///when
        consoleController.createNewUser(userDto);
        Boolean loginPassed = consoleController.loginUser(loginCredentials);
        ///then

        assertFalse(loginPassed);
        assertTrue(outContent.toString().contains("Wrong password provided, try again"));
    }



    @Test
    public void passwordEncryptionTest() {
        ///given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", "123456789");

        ///when
        consoleController.createNewUser(userDto);

        ///then
        assertNotEquals(userRepository.findByUsername("testUsername").getPassword(), userDto.getPassword());
    }

    @Test
    public void updateEmailTest() {
        ///given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", "123456789");
        consoleController.createNewUser(userDto);

        ///when
        consoleController.updateEmail("testUsername", "newMail@test.com");

        ///then
        assertEquals(userRepository.findByUsername("testUsername").getEmail(), "newMail@test.com");
    }

    @Test
    public void updateEmailWrongFormTest(){
        ///given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", "123456789");
        consoleController.createNewUser(userDto);

        ///when
        consoleController.updateEmail("testUsername", "wrongEmailForm");

        ///then
        assertEquals("testMail@test.com", userRepository.findByUsername("testUsername").getEmail());
        assertTrue(outContent.toString().contains("Wrong form of email provided, try again"));
    }

    @Test
    public void updateUserTelephoneTest() {
        ///given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", "123456789");
        consoleController.createNewUser(userDto);

        ///when
        consoleController.updateTelephoneNumber("testUsername", "987654321");

        ///then
        assertEquals(userRepository.findByUsername("testUsername").getTelephoneNumber(), "987654321");
    }

    @Test
    public void updateUserTelephoneWrongFormTest(){
        ///given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", "123456789");
        consoleController.createNewUser(userDto);

        ///when
        consoleController.updateTelephoneNumber("testUsername", "wrongTelephoneForm");

        ///then
        assertEquals(userRepository.findByUsername("testUsername").getTelephoneNumber(), "123456789");
        assertTrue(outContent.toString().contains("Wrong form of telephone number provided, try again"));
    }
}