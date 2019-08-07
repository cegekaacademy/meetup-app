package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.ChallengeCategory;
import com.cegeka.academy.domain.Interest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
class ChallengeCategoryTest {

    @Autowired
    private ChallengeCategoryRepository challengeCategoryRepository;

    @Test
    public void testAddChallengeCategory(){
        ChallengeCategory challengeCategory =  new ChallengeCategory();
        challengeCategory.setName("category1");
        challengeCategory.setDescription("description1");
        challengeCategoryRepository.save(challengeCategory);
        assertThat(challengeCategoryRepository.findAll().size()).isEqualTo(1);
        assertThat(challengeCategoryRepository.findAll().get(0).getName()).isEqualTo("category1");
        assertThat(challengeCategoryRepository.findAll().get(0).getDescription()).isEqualTo("description1");
    }

    @Test
    public void testFindByName(){
        ChallengeCategory challengeCategory =  new ChallengeCategory();
        challengeCategory.setName("category1");
        challengeCategory.setDescription("description1");
        challengeCategoryRepository.save(challengeCategory);
        assertThat(challengeCategoryRepository.findByName("category1")).isEqualTo(challengeCategoryRepository.findAll().get(0));
    }

    @Test
    public void testFindByNameWithNoResult(){
        ChallengeCategory challengeCategory =  new ChallengeCategory();
        challengeCategory.setName("category1");
        challengeCategory.setDescription("description1");
        challengeCategoryRepository.save(challengeCategory);
        assertThat(challengeCategoryRepository.findByName("category2")).isEqualTo(null);
    }
}