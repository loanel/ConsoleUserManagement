package com.data.test.ConsoleUserManagement;

import com.data.test.ConsoleUserManagement.controller.ConsoleController;
import com.data.test.ConsoleUserManagement.dto.PasswordHolder;
import com.data.test.ConsoleUserManagement.dto.UserDto;
import com.data.test.ConsoleUserManagement.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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
public class ConsoleControllerTests {

    @Autowired
    ConsoleController consoleController;

    @Autowired
    UserRepository userRepository;

    @Test
    public void addUserToDatabase(){
        ///given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", "123456789");

        ///when
        consoleController.addUserToDatabase(userDto);

        ///then
        userRepository.existsByUsername("testUsername");
    }

    @Test
    public void passwordEncryptionTest(){
        ///given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", "123456789");

        ///when
        consoleController.addUserToDatabase(userDto);

        ///then
        assertNotEquals(userRepository.findByUsername("testUsername").getPassword(), userDto.getPassword());
    }

    @Test
    public void changeUserEmail(){
        ///given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", "123456789");
        consoleController.addUserToDatabase(userDto);

        ///when
        consoleController.updateEmail("testUsername", "newMail@test.com");

        ///then

        assertEquals(userRepository.findByUsername("testUsername").getEmail(), "newMail@test.com");
    }

    @Test
    public void changeUserTelephone(){
        ///given
        UserDto userDto = new UserDto("testUsername", new PasswordHolder("testpas!AA1".toCharArray()), "testMail@test.com", "123456789");
        consoleController.addUserToDatabase(userDto);

        ///when
        consoleController.updateTelephoneNumber("testUsername", "987654321");

        ///then
        assertEquals(userRepository.findByUsername("testUsername").getTelephoneNumber(), "987654321");
    }
}
