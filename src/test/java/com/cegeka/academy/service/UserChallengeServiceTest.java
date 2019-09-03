package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.*;
import com.cegeka.academy.domain.enums.InvitationStatus;
import com.cegeka.academy.domain.enums.UserChallengeStatus;
import com.cegeka.academy.repository.*;
import com.cegeka.academy.service.dto.UserChallengeDTO;
import com.cegeka.academy.service.mapper.UserChallengeMapper;
import com.cegeka.academy.service.userChallenge.UserChallengeService;
import com.cegeka.academy.web.rest.errors.InvalidInvitationStatusException;
import com.cegeka.academy.web.rest.errors.InvalidUserChallengeStatusException;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import com.cegeka.academy.web.rest.errors.WrongOwnerException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class UserChallengeServiceTest  {

    @Autowired
    private UserChallengeRepository userChallengeRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private ChallengeCategoryRepository challengeCategoryRepository;

    @Autowired
    private UserChallengeService userChallengeService;

    @Autowired
    private ChallengeAnswerRepository challengeAnswerRepository;

    private Invitation invitation;
    private User user;
    private Challenge challenge, challenge2;
    private ChallengeCategory challengeCategory;
    private UserChallengeDTO userChallengeDTO;
    private UserChallenge userChallenge;
    private ChallengeAnswer challengeAnswer;
    private User usedUser;

    @BeforeEach
    public void init() {

        user = new User();
        user.setLogin("ana");
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail("ana@gmail.com");
        user.setFirstName("ana");
        user.setLastName("doe");
        user.setImageUrl("http://placehold.it/50x50");
        user.setLangKey("mmmm");
        usedUser = userRepository.save(user);

        invitation = new Invitation();
        invitation.setDescription("invitationDescription");
        invitation.setStatus("pending");
        invitation.setUser(usedUser);
        invitation.setEvent(null);
        invitationRepository.save(invitation);

        Date startDate = new Date();
        Date endDate = new Date();

        challengeCategory = new ChallengeCategory();
        challengeCategory.setName("challengeCategory");
        challengeCategory.setDescription("challengeCategoryDescription");
        challengeCategoryRepository.save(challengeCategory);

        challenge = new Challenge();
        challenge.setCreator(usedUser);
        challenge.setPoints(5.22);
        challenge.setStartDate(startDate);
        challenge.setEndDate(endDate);
        challenge.setStatus("new");
        challenge.setDescription("description");
        challenge.setChallengeCategory(challengeCategoryRepository.findAll().get(0));
        challengeRepository.save(challenge);

        challenge2 = new Challenge();
        challenge2.setCreator(usedUser);
        challenge2.setPoints(5.22);
        challenge2.setStartDate(startDate);
        challenge2.setEndDate(endDate);
        challenge2.setStatus("new");
        challenge2.setDescription("description");
        challenge2.setChallengeCategory(challengeCategoryRepository.findAll().get(0));
        challengeRepository.save(challenge2);

        userChallenge = new UserChallenge();
        userChallenge.setUser(usedUser);
        userChallenge.setInvitation(invitationRepository.findAll().get(0));
        userChallenge.setChallenge(challengeRepository.findAll().get(0));
        userChallenge.setStatus("status");
        userChallenge.setPoints(2.22);
        userChallenge.setStartTime(new Date());
        userChallenge.setEndTime(new Date());
        userChallengeRepository.save(userChallenge);

        challengeAnswer = new ChallengeAnswer();
        challengeAnswer.setImagePath("imagePath");
        challengeAnswer.setVideoAt("videoAt");
        challengeAnswer.setAnswer("answer");

        challengeAnswerRepository.save(challengeAnswer);

        userChallengeDTO = UserChallengeMapper.convertUserChallengeToUserChallengeDTO(userChallenge);

    }

    @AfterEach
    public void destroy(){

        invitationRepository.deleteAll();
        userChallengeRepository.deleteAll();
        challengeRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    public void testGetChallengesByUserId(){

       List<UserChallengeDTO> results = userChallengeService.getUserChallengesByUserId(usedUser.getId());
        assertThat(results.get(0).getChallenge().getId()).isEqualTo(userChallenge.getChallenge().getId());
        assertThat(results.get(0).getStartTime()).isEqualTo(userChallenge.getStartTime());
        assertThat(results.get(0).getEndTime()).isEqualTo(userChallenge.getEndTime());
        assertThat(results.get(0).getPoints()).isEqualTo(userChallenge.getPoints());
        assertThat(results.get(0).getStatus()).isEqualTo(userChallenge.getStatus());
        assertThat(results.get(0).getChallengeAnswer()).isEqualTo(userChallenge.getChallengeAnswer());
        assertThat(results.get(0).getInvitation().getId()).isEqualTo(userChallenge.getInvitation().getId());
        assertThat(results.get(0).getUser().getId()).isEqualTo(userChallenge.getUser().getId());

    }

    @Test
    public void testGetChallengesByUserIdWithNoResult(){

        List<UserChallengeDTO> results = userChallengeService.getUserChallengesByUserId(20l);
        assertThat(results.size()).isEqualTo(0);

    }

    @Test
    public void testUpdateUserChallengeStatusIsWorking() throws NotFoundException, InvalidUserChallengeStatusException {

        UserChallenge existingUserChallenge = userChallengeRepository.findAll().get(0);

        userChallengeService.updateUserChallengeStatus(existingUserChallenge.getId(), UserChallengeStatus.ACCEPTED.toString());

        assertThat(userChallengeRepository.findAll().get(0).getId()).isEqualTo(existingUserChallenge.getId());
        assertThat(userChallengeRepository.findAll().get(0).getStatus()).isEqualTo(UserChallengeStatus.ACCEPTED.toString());

    }

    @Test
    public void testUpdateUserChallengeStatusWithInvalidId() {

        Assertions.assertThrows(NotFoundException.class, () -> {

            userChallengeService.updateUserChallengeStatus(100L, UserChallengeStatus.ACCEPTED.toString());

        });

    }

    @Test
    public void testUpdateUserChallengeStatusWithInvalidStatus() {

        Assertions.assertThrows(InvalidUserChallengeStatusException.class, () -> {

            userChallengeService.updateUserChallengeStatus(userChallengeRepository.findAll().get(0).getId(), "status");

        });

    }

    @Test
    public void testUpdateUserChallengeInvitationStatusIsWorking() throws NotFoundException, InvalidInvitationStatusException {

        UserChallenge existingUserChallenge = userChallengeRepository.findAll().get(0);

        userChallengeService.updateUserChallengeInvitationStatus(existingUserChallenge.getId(), InvitationStatus.CANCELED.toString());

        assertThat(userChallengeRepository.findAll().get(0).getId()).isEqualTo(existingUserChallenge.getId());
        assertThat(userChallengeRepository.findAll().get(0).getInvitation().getStatus()).isEqualTo(InvitationStatus.CANCELED.toString());

    }

    @Test
    public void testUpdateUserChallengeInvitationStatusWithInvalidId() {

        Assertions.assertThrows(NotFoundException.class, () -> {

            userChallengeService.updateUserChallengeInvitationStatus(100L, InvitationStatus.CANCELED.toString());

        });

    }

    @Test
    public void testUpdateUserChallengeInvitationStatusWithInvalidStatus() {

        Assertions.assertThrows(InvalidInvitationStatusException.class, () -> {

            userChallengeService.updateUserChallengeInvitationStatus(userChallengeRepository.findAll().get(0).getId(), "status");
        });

    }
    @Test
    public void testRateUserByOwner() throws WrongOwnerException {
        userChallengeDTO.setPoints(49);
        double actual = userChallengeDTO.getPoints();
        double expected = userChallengeService.rateUser(userChallengeDTO, usedUser.getId()).getPoints();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testRateUserByWrongOwner() {
        userChallengeDTO.setPoints(49);

        Assertions.assertThrows(WrongOwnerException.class, () -> {
            userChallengeService.rateUser(userChallengeDTO, (long)4);
        });

    }

    @Test
    public void testRateUserWhenInvitationIdIsWrong() {
        userChallengeDTO.setPoints(49);
        userChallengeDTO.getInvitation().setId((long) 200);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            userChallengeService.rateUser(userChallengeDTO, challenge.getCreator().getId());
        });
    }

    @Test
    public void testRateUserWhenUserIdIsWrong() {
        userChallengeDTO.setPoints(49);
        userChallengeDTO.getUser().setId((long) 7);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            userChallengeService.rateUser(userChallengeDTO, challenge.getCreator().getId());
        });

    }

    @Test
    public void testRateUserWhenChallengeIdIsWrong() {
        userChallengeDTO.setPoints(49);
        userChallengeDTO.getChallenge().setId((long) 5);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            userChallengeService.rateUser(userChallengeDTO, challenge.getCreator().getId());
        });

    }

    @Test
    public void testUpdateAnswerForExistingUserChallengeIsWorking() {

        userChallengeService.updateUserChallengeAnswer(userChallenge, challengeAnswer);

        assertThat(userChallengeRepository.findAllByChallengeAnswerId(challengeAnswer.getId()).size()).isEqualTo(1);
    }

    @Test
    public void testInitUserChallengeIsWorking() throws NotFoundException {

        userChallengeService.initUserChallenge(challenge2, invitation);

        Optional<UserChallenge> userChallengeOptional = userChallengeRepository
                .findByUserIdAndChallengeIdAndInvitationId(
                        invitation.getUser().getId(),
                        challenge2.getId(),
                        invitation.getId());
        assertThat(userChallengeOptional.isPresent()).isEqualTo(true);
    }

    @Test
    public void testInitUserChallengeThrowsNotFoundExceptionForMissingChallenge() throws NotFoundException {

        Challenge temporaryChallenge = new Challenge();
        temporaryChallenge.setId((long)200);

        Assertions.assertThrows(NotFoundException.class,
                () -> userChallengeService.initUserChallenge(temporaryChallenge, invitation));

    }

    @Test
    public void testInitUserChallengeThrowsNotFoundExceptionForMissingInvitation() {
        Invitation temporaryInvitation = new Invitation();
        temporaryInvitation.setId((long)200);

        Assertions.assertThrows(NotFoundException.class,
                () -> userChallengeService.initUserChallenge(challenge2, temporaryInvitation));
    }

    @Test
    public void testInitUserChallengeThrowsNotFoundExceptionForMissingUser() {
        User temporaryUser = new User();
        temporaryUser.setId((long)200);

        Invitation temporaryInvitation = new Invitation();
        temporaryInvitation.setId((long)5);
        temporaryInvitation.setUser(temporaryUser);

        Assertions.assertThrows(NotFoundException.class,
                () -> userChallengeService.initUserChallenge(challenge2, temporaryInvitation));
    }
}
