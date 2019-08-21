package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.*;
import com.cegeka.academy.repository.*;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class ChallengeServiceTest {

    @Autowired
    private ChallengeService challengeService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChallengeCategoryRepository challengeCategoryRepository;
    @Autowired
    private ChallengeRepository challengeRepository;
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private UserChallengeRepository userChallengeRepository;

    private UserChallenge userChallenge;
    private UserChallenge userChallenge2;
    private Invitation invitation;
    private Challenge challenge;
    private Challenge challenge2;

    private ChallengeDTO challengeDTO;
    private UserDTO userDTO;
    private ChallengeCategoryDTO challengeCategoryDTO;

    long defaultChallengeId;

    @BeforeEach
    public void init(){

        User user;
        ChallengeCategory challengeCategory;

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

        challenge2 = new Challenge();
        challenge2.setCreator(user);
        challenge2.setPoints(5.22);
        challenge2.setStartDate(new Date(System.currentTimeMillis()));
        challenge2.setEndDate(new Date(System.currentTimeMillis()));
        challenge2.setStatus("new");
        challenge2.setDescription("description");
        challenge2.setChallengeCategory(challengeCategory);

        invitation = new Invitation();
        invitation.setDescription("invitationDescription");
        invitation.setStatus("invitationStatus");
        invitation.setUser(user);
        invitation.setEvent(null);

        userChallenge = new UserChallenge();
        userChallenge.setUser(user);
        userChallenge.setInvitation(invitation);
        userChallenge.setChallenge(challenge);
        userChallenge.setStatus("status");
        userChallenge.setPoints(2.22);
        userChallenge.setStartTime(new Date());
        userChallenge.setEndTime(new Date());


        userChallenge2 = new UserChallenge();
        userChallenge2.setUser(user);
        userChallenge2.setInvitation(invitation);
        userChallenge2.setChallenge(challenge2);
        userChallenge2.setStatus("status");
        userChallenge2.setPoints(2.22);
        userChallenge2.setStartTime(new Date());
        userChallenge2.setEndTime(new Date());
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

    @Test
    void NotFoundExceptionTest() {
        Assertions.assertThrows(NotFoundException.class,() -> {challengeService.deleteChallenge(0);});
    }

    @Test
    void deleteTest() throws NotFoundException {
        defaultChallengeId = challengeRepository.save(challenge).getId();
        challengeService.deleteChallenge(defaultChallengeId);
        Assertions.assertFalse(challengeRepository.findById(defaultChallengeId).isPresent());
    }

    @Test
    void EmptyChallengeSetException()
    {
        Assertions.assertThrows(NotFoundException.class,()->{ challengeService.getChallengesByUserId(0); });
    }

    @Test
    void getChallengesByUserIdTest() throws NotFoundException {

        invitationRepository.save(invitation);
        challengeRepository.save(challenge);
        challengeRepository.save(challenge2);
        userChallengeRepository.save(userChallenge);
        userChallengeRepository.save(userChallenge2);

        Set<ChallengeDTO> challengeDTOSet = challengeService.getChallengesByUserId(userDTO.getId());

        Assertions.assertTrue(challengeDTOSet.contains(ChallengeMapper.convertChallengeToChallengeDTO(challenge)));
        Assertions.assertTrue(challengeDTOSet.contains(ChallengeMapper.convertChallengeToChallengeDTO(challenge2)));
    }

    @AfterEach
    public void destroy(){
        userChallengeRepository.deleteAll();
        challengeRepository.deleteAll();
        challengeCategoryRepository.deleteAll();
        invitationRepository.deleteAll();
        userRepository.deleteAll();
    }

}
