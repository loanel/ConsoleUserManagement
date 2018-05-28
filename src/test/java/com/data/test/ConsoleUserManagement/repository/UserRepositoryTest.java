package com.data.test.ConsoleUserManagement.repository;

import com.data.test.ConsoleUserManagement.ConsoleUserManagementApplicationTest;
import com.data.test.ConsoleUserManagement.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles(value = "test")
@SpringBootTest(classes = ConsoleUserManagementApplicationTest.class)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void checkUserExistsByUsername() {
        ///given
        User user = new User("testUser", "testpass", "testMail@gmail.com", "123456789");
        entityManager.persist(user);
        entityManager.flush();

        ///when
        Boolean userExists = userRepository.existsByUsername(user.getUsername());

        ///then
        assertTrue(userExists);
    }

    @Test
    public void checkUserDoesNotExist() {
        ///when
        Boolean userExists = userRepository.existsByUsername("nonExistingUser");

        ///then
        assertFalse(userExists);
    }

    @Test
    public void findUserByUsername() {
        ///given
        User user = new User("testUser", "testpass", "testMail@gmail.com", "123456789");
        entityManager.persist(user);
        entityManager.flush();

        ///when
        User foundUser = userRepository.findByUsername(user.getUsername());

        ///then
        assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void findNonExistingByUsername() {
        ///when
        User foundUser = userRepository.findByUsername("nonExistingUser");

        ///then
        assertNull(foundUser);
    }

    @Test
    public void saveMethodTest() {
        ///given
        User user = new User("testUser", "testpass", "testMail@gmail.com", "123456789");

        ///when
        userRepository.save(user);

        ///then
        assertTrue(userRepository.existsByUsername("testUser"));
    }

    @Test
    public void deleteMethodTest() {
        ///given
        User user = new User("testUser", "testpass", "testMail@gmail.com", "123456789");
        userRepository.save(user);

        ///then
        userRepository.delete(user);

        ///then
        assertFalse(userRepository.existsByUsername("testUser"));
    }
}
