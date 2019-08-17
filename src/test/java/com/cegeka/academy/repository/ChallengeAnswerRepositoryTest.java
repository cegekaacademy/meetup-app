package com.cegeka.academy.repository;


import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.ChallengeAnswer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class ChallengeAnswerRepositoryTest {

    @Autowired
    private ChallengeAnswerRepository challengeAnswerRepository;

    private ChallengeAnswer challengeAnswer;

    @BeforeEach
    public void setUp(){

        challengeAnswer = new ChallengeAnswer();
        challengeAnswer.setImagePath("imagePath");
        challengeAnswer.setVideoAt("videoAt");
        challengeAnswer.setAnswer("answer");

    }

    @BeforeEach
    public void destroy(){

        challengeAnswerRepository.deleteAll();
    }

    @Test
    public void testSaveChallengeAnswer(){

        assertThat(challengeAnswerRepository.save(challengeAnswer)).isEqualTo(challengeAnswerRepository.findAll().get(challengeAnswerRepository.findAll().size() - 1));

    }


}
