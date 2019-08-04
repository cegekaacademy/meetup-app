package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.config.audit.AuditEventConverter;
import com.cegeka.academy.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class UserChallengeTest {

    @Autowired
    UserChallengeRepository userChallengeRepository;

    @Autowired
    InvitationRepository invitationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChallengeRepository challengeRepository;

    Invitation invitation = new Invitation();
    User user = new User();
    Challenge challenge = new Challenge();

    @Test
    public void testAddUserChallenge() {

        user.setLogin("LoginSetForTest");
        user.setPassword("42jIG0vHCTEWClhT5R2om2V5NpgOXNcQggP6YJOz2xMccBQzGDWgqUDLKOqZ");

        invitationRepository.save(invitation);
        userRepository.save(user);
        challengeRepository.save(challenge);


        UserChallenge userChallenge = new UserChallenge();
        userChallenge.setUser(user);
        userChallenge.setInvitation(invitation);
        userChallenge.setChallenge(challenge);
        userChallenge.setStatus("Active");
        userChallenge.setPoints(0);
        userChallenge.setStartTime(new Date());
        userChallenge.setEndTime(new Date());
        userChallengeRepository.save(userChallenge);

        UserChallenge userChallenge1 = new UserChallenge();
        userChallenge1.setUser(user);
        userChallenge1.setInvitation(invitation);
        userChallenge1.setChallenge(challenge);
        userChallenge1.setStatus("Active");
        userChallenge1.setPoints(0);
        userChallenge1.setStartTime(new Date());
        userChallenge1.setEndTime(new Date());
        userChallengeRepository.save(userChallenge1);

        UserChallenge userChallenge2 = new UserChallenge();
        userChallenge2.setUser(user);
        userChallenge2.setInvitation(invitation);
        userChallenge2.setChallenge(challenge);
        userChallenge2.setStatus("Active");
        userChallenge2.setPoints(0);
        userChallenge2.setStartTime(new Date());
        userChallenge2.setEndTime(new Date());
        userChallengeRepository.save(userChallenge2);


        assertThat(userChallengeRepository.findAll().size()).isEqualTo(3);
    }
}
