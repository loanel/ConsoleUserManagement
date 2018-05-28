package com.data.test.ConsoleUserManagement.dto;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class UserDtoTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void usernameIsNull() {
        ///given
        Set<ConstraintViolation<UserDto>> constraintViolations;

        ///when
        UserDto userDto = new UserDto(null, new PasswordHolder("testP1?ss".toCharArray()), "testMail@test.com", "123456789");
        constraintViolations = validator.validate(userDto);

        ///then
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Username can't be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void usernameLengthShort() {
        ///given
        Set<ConstraintViolation<UserDto>> constraintViolations;

        ///when
        UserDto userDto = new UserDto("ab", new PasswordHolder("testP1?ss".toCharArray()), "testMail@test.com", "123456789");
        constraintViolations = validator.validate(userDto);

        ///then
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Username is too short, minimum 3 characters required", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void passwordIsNull() {
        ///given
        Set<ConstraintViolation<UserDto>> constraintViolations;

        ///when
        UserDto userDto = new UserDto("testUsername", null, "testMail@test.com", "123456789");
        constraintViolations = validator.validate(userDto);

        ///then
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Password can't be null", constraintViolations.iterator().next().getMessage());
    }


    @Test
    public void passwordLengthShort() {
        ///given
        Set<ConstraintViolation<UserDto>> constraintViolations;

        ///when
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("t!A3pas".toCharArray()), "testMail@test.com", "123456789");
        constraintViolations = validator.validate(userDto);

        ///then
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Password is too short, minimum 8 characters required", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void passwordNoSmallCharacter() {
        ///given
        Set<ConstraintViolation<UserDto>> constraintViolations;

        ///when
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("TESTPAS!3".toCharArray()), "testMail@test.com", "123456789");
        constraintViolations = validator.validate(userDto);

        ///then
        assertFalse(constraintViolations.isEmpty());
        assertEquals("No small character in password", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void passwordNoLargeCharacter() {
        ///given
        Set<ConstraintViolation<UserDto>> constraintViolations;

        ///when
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas?3".toCharArray()), "testMail@test.com", "123456789");
        constraintViolations = validator.validate(userDto);

        ///then
        assertFalse(constraintViolations.isEmpty());
        assertEquals("No large character in password", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void passwordNoSpecialCharacter() {
        ///given
        Set<ConstraintViolation<UserDto>> constraintViolations;

        ///when
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas3A".toCharArray()), "testMail@test.com", "123456789");
        constraintViolations = validator.validate(userDto);

        ///then
        assertFalse(constraintViolations.isEmpty());
        assertEquals("No special character in password", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void passwordNoNumber() {
        ///given
        Set<ConstraintViolation<UserDto>> constraintViolations;

        ///when
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA".toCharArray()), "testMail@test.com", "123456789");
        constraintViolations = validator.validate(userDto);

        ///then
        assertFalse(constraintViolations.isEmpty());
        assertEquals("No number in password", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void emailIsNull() {
        ///given
        Set<ConstraintViolation<UserDto>> constraintViolations;

        ///when
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), null, "123456789");
        constraintViolations = validator.validate(userDto);

        ///then
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Email can't be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void wrongEmailFormat() {
        // email requires a @ in it
        ///given
        Set<ConstraintViolation<UserDto>> constraintViolations;

        ///when
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMailWrongFormat", "123456789");
        constraintViolations = validator.validate(userDto);

        ///then
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Wrong email, please input a proper email address", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void telephoneNumberIsNull() {
        ///given
        Set<ConstraintViolation<UserDto>> constraintViolations;

        ///when
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", null);
        constraintViolations = validator.validate(userDto);

        ///then
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Telephone number can't be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void wrongTelephoneNumber() {
        // email requires a @ in it
        ///given
        Set<ConstraintViolation<UserDto>> constraintViolations;

        ///when
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", "aaaaa");
        constraintViolations = validator.validate(userDto);

        ///then
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Wrong telephone number, please only provide digits in the number", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void correctUserInput() {
        ///given
        Set<ConstraintViolation<UserDto>> constraintViolations;

        ///when
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", "123456789");
        constraintViolations = validator.validate(userDto);

        ///then
        assertTrue(constraintViolations.isEmpty());
    }


}
