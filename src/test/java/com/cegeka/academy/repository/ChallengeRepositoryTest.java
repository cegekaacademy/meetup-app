package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.ChallengeCategory;
import com.cegeka.academy.domain.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class ChallengeRepositoryTest {

    private static final String DEFAULT_LOGIN = "johndoe";

    private static final String DEFAULT_EMAIL = "johndoe@localhost";

    private static final String DEFAULT_FIRSTNAME = "john";

    private static final String DEFAULT_LASTNAME = "doe";

    private static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";

    private static final String DEFAULT_LANGKEY = "dummy";

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChallengeCategoryRepository challengeCategoryRepository;

    private User user;

    @BeforeEach
    public void init() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail(DEFAULT_EMAIL);
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setImageUrl(DEFAULT_IMAGEURL);
        user.setLangKey(DEFAULT_LANGKEY);
    }

    @Test
    public void canAddChallenge() {
        Challenge challenge = new Challenge();
        Date startDate = new Date();
        Date endDate = new Date();

        User userTest = userRepository.save(user);

        challenge.setCreator(userTest);
        challenge.setPoints(5.22);
        challenge.setStartDate(startDate);
        challenge.setEndDate(endDate);
        challenge.setStatus("new");

        Challenge challengeTest = challengeRepository.save(challenge);

        assertThat(challengeTest).isEqualTo(challenge);
    }
    @Test
    public void canAddChallengeWithDescriptionAndCategory() {

        Challenge challenge = new Challenge();
        Date startDate = new Date();
        Date endDate = new Date();

        User userTest = userRepository.save(user);

        ChallengeCategory challengeCategory = new ChallengeCategory();
        challengeCategory.setName("challengeCategory");
        challengeCategory.setDescription("challengeCategoryDescription");
        challengeCategoryRepository.save(challengeCategory);

        challenge.setCreator(userTest);
        challenge.setPoints(22.0);
        challenge.setStartDate(startDate);
        challenge.setEndDate(endDate);
        challenge.setStatus("status");
        challenge.setDescription("description");
        challenge.setChallengeCategory(challengeCategoryRepository.findAll().get(0));

        challengeRepository.save(challenge);
        Challenge challengeResult = challengeRepository.findAll().get(0);

        assertThat(challengeResult).isEqualTo(challenge);
    }
}
