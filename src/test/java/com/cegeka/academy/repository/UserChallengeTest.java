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
    private UserChallengeRepository userChallengeRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    private Invitation invitation = new Invitation();
    private User user = new User();
    private Challenge challenge = new Challenge();

    @BeforeEach
    public void init() {
        invitationRepository.save(invitation);

        user.setLogin("LoginSetForTest");
        user.setPassword("42jIG0vHCTEWClhT5R2om2V5NpgOXNcQggP6YJOz2xMccBQzGDWgqUDLKOqZ");
        userRepository.save(user);

        challenge.setStartDate(new Date());
        challenge.setEndDate(new Date());
        challenge.setStatus("Active");
        challenge.setCreator(user);
        challengeRepository.save(challenge);
    }

    public UserChallenge initObject() {
        UserChallenge userChallenge = new UserChallenge();
        userChallenge.setUser(user);
        userChallenge.setInvitation(invitation);
        userChallenge.setChallenge(challenge);
        userChallenge.setStatus("Active");
        userChallenge.setPoints(0);
        userChallenge.setStartTime(new Date());
        userChallenge.setEndTime(new Date());

        return userChallenge;
    }

    @Test
    public void testAddUserChallenge() {

        userChallengeRepository.save(initObject());
        userChallengeRepository.save(initObject());
        userChallengeRepository.save(initObject());

        assertThat(userChallengeRepository.findAll().size()).isEqualTo(3);
    }
}
