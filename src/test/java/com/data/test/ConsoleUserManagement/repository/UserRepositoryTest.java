package com.data.test.ConsoleUserManagement.repository;

import com.data.test.ConsoleUserManagement.ConsoleUserManagementApplicationTest;
import com.data.test.ConsoleUserManagement.model.User;
import com.data.test.ConsoleUserManagement.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

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
    public void checkUserExistsByUsername(){
        ///given
        User user = new User("testUser", "testpass", "123456789", "testMail@gmail.com");
        entityManager.persist(user);
        entityManager.flush();

        ///when
        Boolean userExists = userRepository.existsByUsername(user.getUsername());

        ///then
        assertTrue(userExists);
    }

    @Test
    public void findUserByUsername(){
        ///given
        User user = new User("testUser", "testpass", "123456789", "testMail@gmail.com");
        entityManager.persist(user);
        entityManager.flush();

        ///when
        User foundUser = userRepository.findByUsername(user.getUsername());

        ///then
        assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
    }
}
