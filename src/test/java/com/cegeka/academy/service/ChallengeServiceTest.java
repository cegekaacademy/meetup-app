package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    private ChallengeDTO challengeDTO;
    private UserDTO userDTO;
    private ChallengeCategoryDTO challengeCategoryDTO;

    @BeforeEach
    public void init(){

        User aux = new User();
        aux.setLogin("login");
        aux.setPassword("anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        Long idUser = userRepository.save(aux).getId();

        userDTO = new UserDTO(aux);
        userDTO.setId(idUser);

        challengeCategoryDTO = new ChallengeCategoryDTO();
        challengeCategoryDTO.setDescription("description");
        challengeCategoryDTO.setName("name");
        Long challengeCategoryId = challengeCategoryRepository.save(ChallengeMapper.convertChallengeCategoryDTOToChallengeCategory(challengeCategoryDTO)).getId();
        challengeCategoryDTO.setId(challengeCategoryId);

        challengeDTO = new ChallengeDTO();
        challengeDTO.setCreator(userDTO);
        challengeDTO.setChallengeCategory(challengeCategoryDTO);
        challengeDTO.setDescription("description");
        challengeDTO.setEndDate(new Date());
        challengeDTO.setStartDate(new Date());
        challengeDTO.setPoints(22);
        challengeDTO.setStatus("status");
    }

    @Test
    public void testSaveChallenge(){

      challengeService.saveChallenge(challengeDTO);

       assertThat(challengeRepository.findAll().get(0).getCreator()).isEqualTo(new UserMapper().userDTOToUser(challengeDTO.getCreator()));
       assertThat(challengeRepository.findAll().get(0).getChallengeCategory()).isEqualTo(ChallengeMapper.convertChallengeCategoryDTOToChallengeCategory(challengeDTO.getChallengeCategory()));
       assertThat(challengeRepository.findAll().get(0).getDescription()).isEqualTo(challengeDTO.getDescription());
       assertThat(challengeRepository.findAll().get(0).getStartDate()).isEqualTo(challengeDTO.getStartDate());
       assertThat(challengeRepository.findAll().get(0).getEndDate()).isEqualTo(challengeDTO.getEndDate());
       assertThat(challengeRepository.findAll().get(0).getStatus()).isEqualTo(challengeDTO.getStatus());
       assertThat(challengeRepository.findAll().get(0).getPoints()).isEqualTo(challengeDTO.getPoints());

    }

    @AfterEach
    public void destroy(){
        if(userDTO != null){
            userRepository.delete(new UserMapper().userDTOToUser(userDTO));
        }

        if(challengeCategoryDTO != null){
            challengeCategoryRepository.delete(ChallengeMapper.convertChallengeCategoryDTOToChallengeCategory(challengeCategoryDTO));
        }

        if(challengeDTO != null){
            challengeRepository.delete(ChallengeMapper.convertChallengeDTOToChallenge(challengeDTO));
        }
    }

}
