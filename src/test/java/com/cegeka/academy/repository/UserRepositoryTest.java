package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void init() {

        userRepository.deleteAll();
        user = TestsRepositoryUtil.createUser("login1", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        user.setFirstName("ana");
        user.setLastName("maria");
        userRepository.save(user);
    }

    @Test
    public void assertThatSaveUserIsWorking() {

        assertThat(userRepository.findAll().size()).isEqualTo(1);
        assertThat(userRepository.findAll().get(0).getLogin()).isEqualTo(user.getLogin());
        assertThat(userRepository.findAll().get(0).getPassword()).isEqualTo(user.getPassword());
        assertThat(userRepository.findAll().get(0).getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userRepository.findAll().get(0).getLastName()).isEqualTo(user.getLastName());

    }

    @Test
    public void assertThatFindAllByFirstNameAndLastNameIsWorkingWithValidArguments() {

        List<User> findUser = userRepository.findAllByFirstNameAndLastName("ana", "maria");
        assertThat(findUser.get(0)).isEqualTo(userRepository.findAll().get(0));
    }

    @Test
    public void assertThatFindAllByFirstNameAndLastNameIsWorkingWithInvalidFirstName() {

        List<User> findUser = userRepository.findAllByFirstNameAndLastName("ana1", "maria");
        assertThat(findUser.size()).isEqualTo(0);
    }

    @Test
    public void assertThatFindAllByFirstNameAndLastNameIsWorkingWithInvalidLastName() {

        List<User> findUser = userRepository.findAllByFirstNameAndLastName("ana", "maria1");
        assertThat(findUser.size()).isEqualTo(0);
    }

    @Test
    public void assertThatFindAllByFirstNameAndLastNameIsWorkingWithNullFirstName() {

        List<User> findUser = userRepository.findAllByFirstNameAndLastName(null, "maria");
        assertThat(findUser.size()).isEqualTo(0);
    }

    @Test
    public void assertThatFindAllByFirstNameAndLastNameIsWorkingWithNullLastName() {

        List<User> findUser = userRepository.findAllByFirstNameAndLastName("ana", null);
        assertThat(findUser.size()).isEqualTo(0);
    }

    @Test
    public void assertThatFindAllByFirstNameAndLastNameIsWorkingWithTwoResults() {

        User user2 = TestsRepositoryUtil.createUser("login2", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        user2.setFirstName("ana");
        user2.setLastName("maria");
        userRepository.save(user2);
        List<User> findUser = userRepository.findAllByFirstNameAndLastName("ana", "maria");
        assertThat(findUser.size()).isEqualTo(2);
    }
}
