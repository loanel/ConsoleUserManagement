package com.data.test.ConsoleUserManagement.service;

import com.data.test.ConsoleUserManagement.ConsoleUserManagementApplicationTest;
import com.data.test.ConsoleUserManagement.dto.LoginCredentials;
import com.data.test.ConsoleUserManagement.dto.PasswordHolder;
import com.data.test.ConsoleUserManagement.dto.UserDto;
import com.data.test.ConsoleUserManagement.exception.UserAlreadyExistsException;
import com.data.test.ConsoleUserManagement.exception.WrongLoginCredentialsException;
import com.data.test.ConsoleUserManagement.repository.UserRepository;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles(value = "test")
@SpringBootTest(classes = ConsoleUserManagementApplicationTest.class)
@ComponentScan(basePackages = {
        "com.data.test.ConsoleUserManagement.controller",
        "com.data.test.ConsoleUserManagement.service",
        "com.data.test.ConsoleUserManagement.repository",
})
public class ConsoleServiceTest {

    @Autowired
    ConsoleService consoleService;

    @Autowired
    UserRepository userRepository;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void addUserToDatabaseTest() throws UserAlreadyExistsException {
        ///given
        UserDto userDto = new UserDto("testUser", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@gmail.com", "123456789");

        ///when
        consoleService.addUserToDatabase(userDto);

        ///then
        assertTrue(userRepository.existsByUsername("testUser"));
    }

    @Test
    public void addUserExistingInDatabase() throws UserAlreadyExistsException {
        ///given
        UserDto userDto = new UserDto("testUser", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@gmail.com", "123456789");
        UserDto duplicateUserDto = new UserDto("testUser", new PasswordHolder("testpas!AA1".toCharArray()), "123456789", "testMail@gmail.com");

        ///when
        consoleService.addUserToDatabase(userDto);

        ///then
        exception.expect(UserAlreadyExistsException.class);
        consoleService.addUserToDatabase(duplicateUserDto);
    }

    @Test
    public void checkCorrectLoginCredentials() throws UserAlreadyExistsException, WrongLoginCredentialsException {
        ///given
        UserDto userDto = new UserDto("testUser", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@gmail.com", "123456789");
        LoginCredentials loginCredentials = new LoginCredentials("testUser", new PasswordHolder("testpas!AA1".toCharArray()));
        consoleService.addUserToDatabase(userDto);

        ///then
        consoleService.checkLoginCredentials(loginCredentials);
    }

    @Test
    public void checkWrongUsernameCredentials() throws UserAlreadyExistsException, WrongLoginCredentialsException {
        ///given
        UserDto userDto = new UserDto("testUser", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@gmail.com", "123456789");
        LoginCredentials loginCredentials = new LoginCredentials("testWrongUsername", new PasswordHolder("testpas!AA1".toCharArray()));
        consoleService.addUserToDatabase(userDto);

        ///then
        exception.expect(WrongLoginCredentialsException.class);
        exception.expectMessage("Wrong username provided, try again");
        consoleService.checkLoginCredentials(loginCredentials);
    }

    @Test
    public void checkWrongPasswordCredentials() throws UserAlreadyExistsException, WrongLoginCredentialsException {
        ///given
        UserDto userDto = new UserDto("testUser", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@gmail.com", "123456789");
        LoginCredentials loginCredentials = new LoginCredentials("testUser", new PasswordHolder("wrongPassword".toCharArray()));
        consoleService.addUserToDatabase(userDto);

        ///then
        exception.expect(WrongLoginCredentialsException.class);
        exception.expectMessage("Wrong password provided, try again");
        consoleService.checkLoginCredentials(loginCredentials);
    }

    @Test
    public void updateUserEmailTest() throws UserAlreadyExistsException {
        ///given
        UserDto userDto = new UserDto("testUser", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@gmail.com", "123456789");
        consoleService.addUserToDatabase(userDto);

        ///when
        consoleService.updateUserEmail("testUser", "newTestMail@gmail.com");

        ///then
        assertEquals("newTestMail@gmail.com", userRepository.findByUsername("testUser").getEmail());
    }

    @Test
    public void updateUserTelephoneNumberTest() throws UserAlreadyExistsException {
        ///given
        UserDto userDto = new UserDto("testUser", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@gmail.com", "123456789");
        consoleService.addUserToDatabase(userDto);

        ///when
        consoleService.updateUserTelephoneNumber("testUser", "987654321");

        ///then
        assertEquals("987654321", userRepository.findByUsername("testUser").getTelephoneNumber());
    }

}