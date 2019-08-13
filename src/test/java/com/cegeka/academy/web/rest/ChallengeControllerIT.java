package com.cegeka.academy.web.rest;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.ChallengeCategory;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.ChallengeCategoryRepository;
import com.cegeka.academy.repository.ChallengeRepository;
import com.cegeka.academy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest(classes = AcademyProjectApp.class)
public class ChallengeControllerIT {

    private MockMvc restMockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChallengeCategoryRepository challengeCategoryRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @BeforeEach
    void setUp() {

        userRepository.deleteAll();
        challengeRepository.deleteAll();
        challengeCategoryRepository.deleteAll();

        ChallengeController challengeController = new ChallengeController();

        restMockMvc = MockMvcBuilders.standaloneSetup(challengeController).build();

        User user = new User();

        user.setEmail("gigi.gogaie@gmail.com");
        user.setFirstName("Gogaie");
        user.setLastName("Momaie");
        user.setPassword("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        user.setLogin("gigel@purcel.com");

        userRepository.saveAndFlush(user);


        ChallengeCategory challengeCategory = new ChallengeCategory();
        challengeCategory.setDescription("Categoria speciala");
        challengeCategory.setName("BiliRuLoc");

        challengeCategoryRepository.saveAndFlush(challengeCategory);

        Challenge challenge = new Challenge();

        challenge.setCreator(user);
        challenge.setStartDate(new Date(System.currentTimeMillis()));
        challenge.setEndDate(new Date(System.currentTimeMillis()));
        challenge.setStatus("Activa");
        challenge.setDescription("Nimic");
        challenge.setPoints(10);
        challenge.setChallengeCategory(challengeCategoryRepository.findAll().get(0));

        challengeRepository.save(challenge);
    }

    @Test
    void testDeleteChallenge() {

        long expectedChallengeItems;
        long actualChallengeItems;

        expectedChallengeItems = challengeRepository.count() - 1;

        try {
            restMockMvc.perform(delete("/challenge"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        actualChallengeItems = challengeRepository.count();

        assertThat(actualChallengeItems).isNotEqualTo(expectedChallengeItems);
    }
}
