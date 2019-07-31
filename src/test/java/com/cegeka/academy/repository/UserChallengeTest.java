package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.UserChallenge;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class UserChallengeTest {

    @Autowired
    UserChallengeRepository userChallengeRepository;

    @Test
    public void testAddUserChallenge() {
        UserChallenge userChallenge = new UserChallenge();
        userChallenge.setChallengeId((long) 1);
        userChallenge.setUserId((long) 1);
        userChallenge.setInvitationId((long)1);
        userChallenge.setStatus("Active");
        userChallenge.setPoints(0);
        userChallenge.setStartTime(new Date());
        userChallenge.setEndTime(new Date());
        userChallengeRepository.save(userChallenge);

        UserChallenge userChallenge1 = new UserChallenge();
        userChallenge1.setChallengeId((long) 1);
        userChallenge1.setUserId((long) 2);
        userChallenge1.setInvitationId((long)2);
        userChallenge1.setStatus("Active");
        userChallenge1.setPoints(0);
        userChallenge1.setStartTime(new Date());
        userChallenge1.setEndTime(new Date());
        userChallengeRepository.save(userChallenge1);

        UserChallenge userChallenge2 = new UserChallenge();
        userChallenge2.setChallengeId((long) 2);
        userChallenge2.setUserId((long) 2);
        userChallenge2.setInvitationId((long)3);
        userChallenge2.setStatus("Active");
        userChallenge2.setPoints(0);
        userChallenge2.setStartTime(new Date());
        userChallenge2.setEndTime(new Date());
        userChallengeRepository.save(userChallenge2);


        assertThat(userChallengeRepository.findAll().size()).isEqualTo(3);
    }
}
