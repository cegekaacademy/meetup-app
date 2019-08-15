package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.ChallengeCategory;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.ChallengeCategoryRepository;
import com.cegeka.academy.repository.ChallengeRepository;
import com.cegeka.academy.repository.UserRepository;
import com.cegeka.academy.service.challenge.ChallengeServiceImp;
import com.cegeka.academy.web.rest.ChallengeController;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

@SpringBootTest(classes = AcademyProjectApp.class)
public class ChallengeServiceIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChallengeCategoryRepository challengeCategoryRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private ChallengeServiceImp challengeServiceImp;


    @BeforeEach
    void setUp() {
        challengeRepository.deleteAll();
        userRepository.deleteAll();
        challengeCategoryRepository.deleteAll();

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
    void NotFoundExceptionTest() {
        Assertions.assertThrows(NotFoundException.class,() -> {challengeServiceImp.deleteChallenge(0);});
    }

    @Test
    void DeleteTest() throws NotFoundException {
        challengeServiceImp.deleteChallenge(1);
        Assertions.assertFalse(challengeRepository.findById(1l).isPresent());
    }
}
