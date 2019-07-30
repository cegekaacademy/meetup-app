package com.cegeka.academy.repository;

import com.cegeka.academy.domain.UserChallenge;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserChallengeRepositoryTest {

    @Autowired
    UserChallengeRepository userChallengeRepository;

    @Test
    public void testAddUserChallenge() {
        UserChallenge userChallenge = new UserChallenge();
        userChallenge.setChallengeId((long)1);
        userChallenge.setUserId((long)1);
        userChallengeRepository.save(userChallenge);
        userChallenge.setChallengeId((long)2);
        userChallengeRepository.save(userChallenge);
        userChallenge.setUserId((long)2);
        userChallengeRepository.save(userChallenge);


        assertThat(userChallengeRepository.findById((long) 1)).isEqualTo(3);
    }

    @Test
    public void testFindByUserId() {
        UserChallenge userChallenge = new UserChallenge();
        userChallenge.setChallengeId((long)1);
        userChallenge.setUserId((long)1);

        assertThat(userChallengeRepository.findAllByUserId((long)1)).isEqualTo(userChallengeRepository.findAll().get(0));
    }

    @Test
    public void testFindByChallengeId() {
        UserChallenge userChallenge = new UserChallenge();
        userChallenge.setChallengeId((long)1);
        userChallenge.setUserId((long)2);

        assertThat(userChallengeRepository.findAllByChallengeId((long)2)).isEqualTo(userChallengeRepository.findAll().get(1));
    }
}