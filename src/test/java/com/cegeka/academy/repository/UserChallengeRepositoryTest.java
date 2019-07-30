package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.UserChallenge;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class UserChallengeRepositoryTest {

    @Autowired
    UserChallengeRepository repository;

    @Test
    public void testAddUserChallenge() {
        UserChallenge userChallenge = new UserChallenge();
        userChallenge.setChallengeId((long)1);
        userChallenge.setUserId((long)1);
        repository.save(userChallenge);
        userChallenge.setChallengeId((long)2);
        repository.save(userChallenge);
        userChallenge.setUserId((long)2);
        repository.save(userChallenge);


        assertThat(repository.findAll().size()).isEqualTo(3);
    }

    @Test
    public void testFindByUserId() {
        UserChallenge userChallenge = new UserChallenge();
        userChallenge.setChallengeId((long)1);
        userChallenge.setUserId((long)1);

        assertThat(repository.findAllByUserId((long)1)).isEqualTo(repository.findAll().get(0));
    }

    @Test
    public void testFindByChallengeId() {
        UserChallenge userChallenge = new UserChallenge();
        userChallenge.setChallengeId((long)1);
        userChallenge.setUserId((long)2);

        assertThat(repository.findAllByChallengeId((long)2)).isEqualTo(repository.findAll().get(1));
    }
}
