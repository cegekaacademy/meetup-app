package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.ChallengeCategory;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.ChallengeCategoryRepository;
import com.cegeka.academy.repository.ChallengeRepository;
import com.cegeka.academy.repository.UserRepository;
import com.cegeka.academy.service.challenge.ChallengeService;
import com.cegeka.academy.service.dto.ChallengeCategoryDTO;
import com.cegeka.academy.service.dto.ChallengeDTO;
import com.cegeka.academy.service.dto.UserDTO;
import com.cegeka.academy.service.mapper.ChallengeMapper;
import com.cegeka.academy.service.mapper.UserMapper;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class ChallengeServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChallengeCategoryRepository challengeCategoryRepository;
    @Autowired
    private ChallengeService challengeService;
    @Autowired
    private ChallengeRepository challengeRepository;

    private ChallengeCategory challengeCategory;
    private Challenge challenge;
    private User user;

    private ChallengeDTO challengeDTO;
    private UserDTO userDTO;
    private ChallengeCategoryDTO challengeCategoryDTO;

    long defaultChallengeId;

    @BeforeEach
    public void init(){

        user = new User();
        user.setLogin("login");
        user.setPassword("anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        Long idUser = userRepository.save(user).getId();

        userDTO = new UserDTO(user);
        userDTO.setId(idUser);

        challengeCategoryDTO = new ChallengeCategoryDTO();
        challengeCategoryDTO.setDescription("description");
        challengeCategoryDTO.setName("name");

        challengeCategory = ChallengeMapper.convertChallengeCategoryDTOToChallengeCategory(challengeCategoryDTO);

        Long challengeCategoryId = challengeCategoryRepository.save(challengeCategory).getId();

        challengeCategoryDTO.setId(challengeCategoryId);

        challengeDTO = new ChallengeDTO();
        challengeDTO.setCreator(userDTO);
        challengeDTO.setChallengeCategory(challengeCategoryDTO);
        challengeDTO.setDescription("description");
        challengeDTO.setEndDate(new Date());
        challengeDTO.setStartDate(new Date());
        challengeDTO.setPoints(22);
        challengeDTO.setStatus("status");

        challenge = new Challenge();

        challenge.setCreator(user);
        challenge.setStartDate(new Date(System.currentTimeMillis()));
        challenge.setEndDate(new Date(System.currentTimeMillis()));
        challenge.setStatus("Activa");
        challenge.setDescription("Nimic");
        challenge.setPoints(10);
        challenge.setChallengeCategory(challengeCategory);
        defaultChallengeId = challengeRepository.save(challenge).getId();
    }

    @Test
    public void testSaveChallenge(){

       challengeService.saveChallenge(challengeDTO);

       int lastItemIndex = (int)challengeRepository.count() - 1;

       assertThat(challengeRepository.findAll().get(lastItemIndex).getCreator()).isEqualTo(new UserMapper().userDTOToUser(challengeDTO.getCreator()));
       assertThat(challengeRepository.findAll().get(lastItemIndex).getChallengeCategory()).isEqualTo(ChallengeMapper.convertChallengeCategoryDTOToChallengeCategory(challengeDTO.getChallengeCategory()));
       assertThat(challengeRepository.findAll().get(lastItemIndex).getDescription()).isEqualTo(challengeDTO.getDescription());
       assertThat(challengeRepository.findAll().get(lastItemIndex).getStartDate()).isEqualTo(challengeDTO.getStartDate());
       assertThat(challengeRepository.findAll().get(lastItemIndex).getEndDate()).isEqualTo(challengeDTO.getEndDate());
       assertThat(challengeRepository.findAll().get(lastItemIndex).getStatus()).isEqualTo(challengeDTO.getStatus());
       assertThat(challengeRepository.findAll().get(lastItemIndex).getPoints()).isEqualTo(challengeDTO.getPoints());

    }

    @Test
    void NotFoundExceptionTest() {
        Assertions.assertThrows(NotFoundException.class,() -> {challengeService.deleteChallenge(0);});
    }

    @Test
    void deleteTest() throws NotFoundException {
        challengeService.deleteChallenge(defaultChallengeId);
        Assertions.assertFalse(challengeRepository.findById(defaultChallengeId).isPresent());
    }

    @AfterEach
    public void destroy(){
        challengeRepository.deleteAll();
        challengeCategoryRepository.deleteAll();
        userRepository.deleteAll();
    }

}
