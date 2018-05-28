package com.data.test.ConsoleUserManagement.service;

import com.data.test.ConsoleUserManagement.ConsoleUserManagementApplicationTest;
import com.data.test.ConsoleUserManagement.dto.PasswordHolder;
import com.data.test.ConsoleUserManagement.dto.UserDto;
import com.data.test.ConsoleUserManagement.exception.WrongUserInformationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles(value = "test")
@SpringBootTest(classes = ConsoleUserManagementApplicationTest.class)
@ComponentScan(basePackages = {
        "com.data.test.ConsoleUserManagement.controller",
        "com.data.test.ConsoleUserManagement.service",
        "com.data.test.ConsoleUserManagement.repository",
})
public class UserValidatorServiceTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Autowired
    private UserValidatorService userValidatorService;

    @Before
    public void setupStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void checkUsernameMessage() throws WrongUserInformationException {
        ///given
        UserDto userDto = new UserDto("ab", new PasswordHolder("testP1?ss".toCharArray()), "testMail@test.com", "123456789");

        ///when
        exception.expect(WrongUserInformationException.class);
        userValidatorService.validateUser(userDto);
        ///then

        assertThat(outContent.toString().contains("Username is too short, minimum 3 characters required"));
    }

    @Test
    public void checkNullUsernameMessage() throws WrongUserInformationException {
        ///given
        UserDto userDto = new UserDto(null, new PasswordHolder("testP1?ss".toCharArray()), "testMail@test.com", "123456789");

        ///when
        exception.expect(WrongUserInformationException.class);
        userValidatorService.validateUser(userDto);

        ///then
        assertThat(outContent.toString().contains("Username can't be null"));
    }

    @Test
    public void checkNullPasswordMessage() throws WrongUserInformationException {
        //given
        UserDto userDto = new UserDto("testUsername", null, "testMail@test.com", "123456789");

        ///when
        exception.expect(WrongUserInformationException.class);
        userValidatorService.validateUser(userDto);

        ///then
        assertThat(outContent.toString().contains("Password can't be null"));
    }

    @Test
    public void checkPasswordLengthShortMessage() throws WrongUserInformationException {
        //given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("t!A3pas".toCharArray()), "testMail@test.com", "123456789");

        ///when
        exception.expect(WrongUserInformationException.class);
        userValidatorService.validateUser(userDto);

        ///then
        assertThat(outContent.toString().contains("Password is too short, minimum 8 characters required"));
    }

    @Test
    public void checkPasswordNoSmallCharacterMessage() throws WrongUserInformationException {
        //given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("TESTPAS!3".toCharArray()), "testMail@test.com", "123456789");

        ///when
        exception.expect(WrongUserInformationException.class);
        userValidatorService.validateUser(userDto);

        ///then
        assertThat(outContent.toString().contains("No small character in password"));
    }

    @Test
    public void checkPasswordNoLargeCharacterMessage() throws WrongUserInformationException {
        //given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas?3".toCharArray()), "testMail@test.com", "123456789");

        ///when
        exception.expect(WrongUserInformationException.class);
        userValidatorService.validateUser(userDto);

        ///then
        assertThat(outContent.toString().contains("No large character in password"));
    }

    @Test
    public void checkPasswordNotSpecialCharacterMessage() throws WrongUserInformationException {
        //given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas3A".toCharArray()), "testMail@test.com", "123456789");

        ///when
        exception.expect(WrongUserInformationException.class);
        userValidatorService.validateUser(userDto);

        ///then
        assertThat(outContent.toString().contains("No special character in password"));
    }

    @Test
    public void checkPasswordNoNumberMessage() throws WrongUserInformationException {
        //given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA".toCharArray()), "testMail@test.com", "123456789");

        ///when
        exception.expect(WrongUserInformationException.class);
        userValidatorService.validateUser(userDto);

        ///then
        assertThat(outContent.toString().contains("No number in password"));
    }

    @Test
    public void checkEmailIsNullMessage() throws WrongUserInformationException {
        //given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), null, "123456789");

        ///when
        exception.expect(WrongUserInformationException.class);
        userValidatorService.validateUser(userDto);

        ///then
        assertThat(outContent.toString().contains("Email can't be null"));
    }

    @Test
    public void checkWrongEmailFormatMessage() throws WrongUserInformationException {
        //given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMailWrongFormat", "123456789");

        ///when
        exception.expect(WrongUserInformationException.class);
        userValidatorService.validateUser(userDto);

        ///then;
        assertThat(outContent.toString().contains("Wrong email, please input a proper email address"));
    }

    @Test
    public void checkTelephoneNumberIsNullMessage() throws WrongUserInformationException {
        //given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", null);

        ///when
        exception.expect(WrongUserInformationException.class);
        userValidatorService.validateUser(userDto);

        ///then
        assertThat(outContent.toString().contains("Telephone number can't be null"));
    }

    @Test
    public void checkWrongTelephoneNumberMessage() throws WrongUserInformationException {
        //given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", "aaaaa");

        ///when
        exception.expect(WrongUserInformationException.class);
        userValidatorService.validateUser(userDto);

        ///then
        assertThat(outContent.toString().contains("Wrong telephone number, please only provide digits in the number"));
    }

    @Test
    public void validateNewEmailTest() throws WrongUserInformationException {
        ///given
        String email = "testmail@gmail.com";

        ///when
        userValidatorService.validateNewEmail(email);

        ///then
        //passes
    }

    @Test
    public void validateNewEmailWrongFormTest() throws WrongUserInformationException {
        ///given
        String email = "wrongEmailForm";

        ///then
        exception.expect(WrongUserInformationException.class);
        exception.expectMessage("Wrong form of email provided, try again");
        userValidatorService.validateNewEmail(email);
    }

    @Test
    public void validateNewTelephoneNumberTest() throws WrongUserInformationException {
        ///given
        String telephoneNumber = "123456789";

        ///when
        userValidatorService.validateNewTelephoneNumber(telephoneNumber);

        ///then
        //passes
    }

    @Test
    public void validateNewTelephoneNumberWrongFormTest() throws WrongUserInformationException {
        ///given
        String telephoneNumber = "wrongTelephoneNumber";

        ///then
        exception.expect(WrongUserInformationException.class);
        exception.expectMessage("Wrong form of telephone number provided, try again");
        userValidatorService.validateNewTelephoneNumber(telephoneNumber);
    }
}
