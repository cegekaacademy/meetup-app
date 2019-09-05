package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.*;
import com.cegeka.academy.repository.*;
import com.cegeka.academy.service.challengeAnswer.ChallengeAnswerService;
import com.cegeka.academy.service.dto.ChallengeAnswerDTO;
import com.cegeka.academy.service.mapper.ChallengeAnswerMapper;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class ChallengeAnswerServiceTest {

    @Autowired
    private ChallengeAnswerService challengeAnswerService;

    @Autowired
    private ChallengeAnswerRepository challengeAnswerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private ChallengeCategoryRepository challengeCategoryRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private UserChallengeRepository userChallengeRepository;

    private ChallengeAnswer challengeAnswer;

    private Challenge challenge;

    private UserChallenge userChallenge;

    private User user;

    private User usedUser;

    private Invitation invitation;

    private ChallengeCategory challengeCategory;

    private ChallengeAnswerDTO challengeAnswerDTO;

    @BeforeEach
    public void init() throws IOException {

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
        challenge.setCreator(userRepository.findAll().get(0));
        challenge.setPoints(5.22);
        challenge.setStartDate(startDate);
        challenge.setEndDate(endDate);
        challenge.setStatus("new");
        challenge.setDescription("description");
        challenge.setChallengeCategory(challengeCategoryRepository.findAll().get(0));
        challengeRepository.save(challenge);

        challengeAnswer = new ChallengeAnswer();
        challengeAnswer.setVideoAt("videoAt");
        challengeAnswer.setAnswer("answer");
        challengeAnswerRepository.save(challengeAnswer);

        userChallenge = new UserChallenge();
        userChallenge.setUser(usedUser);
        userChallenge.setInvitation(invitation);
        userChallenge.setChallenge(challenge);
        userChallenge.setChallengeAnswer(challengeAnswer);
        userChallenge.setStatus("status");
        userChallenge.setPoints(2.22);
        userChallenge.setStartTime(new Date());
        userChallenge.setEndTime(new Date());
        userChallengeRepository.save(userChallenge);

        challengeAnswerDTO = initChallengeAnswerDTO();
    }

    public ChallengeAnswerDTO initChallengeAnswerDTO() throws IOException {
        File image = new File("src/test/resources/images/poza123.jpg");
        FileInputStream input = new FileInputStream(image);

        MultipartFile imageFile = new MockMultipartFile("image", IOUtils.toByteArray(input));

        ChallengeAnswerDTO challengeAnswerDTO = new ChallengeAnswerDTO();
        challengeAnswerDTO.setImage(imageFile);
        challengeAnswerDTO.setAnswer("Answer");
        challengeAnswerDTO.setVideoAt("https://youtube.com/");

        return challengeAnswerDTO;
    }

    @AfterEach
    public void destroy() {

        if (invitation != null) {
            invitationRepository.delete(invitation);
        }

        if (user != null) {
            userRepository.delete(user);
        }

        if (challengeCategory != null) {
            challengeCategoryRepository.delete(challengeCategory);
        }

        if (challenge != null) {
            challengeRepository.delete(challenge);
        }

        if (userChallenge != null) {
            userChallengeRepository.delete(userChallenge);
        }

        if (challengeAnswer != null) {
            challengeAnswerRepository.delete(challengeAnswer);
        }
    }


    @Test
    public void testSaveChallengeAnswer() {

        challengeAnswerService.saveChallengeAnswer(ChallengeAnswerMapper.convertChallengeAnswerToChallengeAnswerDTO(challengeAnswer));
        assertThat(challengeAnswerRepository.findAll().get(0).getAnswer()).isEqualTo(challengeAnswer.getAnswer());
        assertThat(challengeAnswerRepository.findAll().get(0).getImagePath()).isEqualTo(challengeAnswer.getImagePath());
        assertThat(challengeAnswerRepository.findAll().get(0).getVideoAt()).isEqualTo(challengeAnswer.getVideoAt());

    }

    @Test
    public void testUpdateChallengeAnswer() throws NotFoundException {

        challengeAnswerService.saveChallengeAnswer(ChallengeAnswerMapper.convertChallengeAnswerToChallengeAnswerDTO(challengeAnswer));

        ChallengeAnswer existingChallenge = challengeAnswerRepository.findAll().get(0);
        existingChallenge.setAnswer("answer2");

        challengeAnswerService.updateChallengeAnswer(existingChallenge.getId(), ChallengeAnswerMapper.convertChallengeAnswerToChallengeAnswerDTO(existingChallenge));

        assertThat(challengeAnswerRepository.findAll().get(0).getAnswer()).isEqualTo(existingChallenge.getAnswer());
        assertThat(challengeAnswerRepository.findAll().get(0).getImagePath()).isEqualTo(existingChallenge.getImagePath());
        assertThat(challengeAnswerRepository.findAll().get(0).getVideoAt()).isEqualTo(existingChallenge.getVideoAt());

    }

    @Test
    public void testUpdateNoExistingChallengeAnswer() {

        Assertions.assertThrows(NotFoundException.class, () -> {
            challengeAnswerService.updateChallengeAnswer(22L, ChallengeAnswerMapper.convertChallengeAnswerToChallengeAnswerDTO(challengeAnswer));

        });

    }

    @Test
    public void testDeleteChallengeAnswerByUserIdAndChallengeId() throws NotFoundException {

        challengeAnswerService.saveChallengeAnswer(ChallengeAnswerMapper.convertChallengeAnswerToChallengeAnswerDTO(challengeAnswer));
        userChallenge.setChallengeAnswer(challengeAnswerRepository.findAll().get(0));
        userChallengeRepository.save(userChallenge);

        challengeAnswerService.deleteChallengeAnswer(usedUser.getId(), challengeRepository.findAll().get(0).getId());

        assertThat(challengeAnswerRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void testDeleteNoExistingChallengeAnswerByUserIdAndChallengeId() {

        Assertions.assertThrows(NotFoundException.class, () -> {
            challengeAnswerService.deleteChallengeAnswer(100L, 120L);
        });
    }

    @Test
    public void testUploadAnswerIsWorking() throws IOException, NotFoundException {

        challengeAnswerService.uploadAnswerPhoto(challengeAnswer.getId(), challengeAnswerDTO);

        assertThat(challengeAnswer.getImagePath()).isNotEqualTo(null);
    }

    @Test
    public void testUploadAnswerThrowsNotFoundExceptionForMissingUserChallenge() {

        Assertions.assertThrows(NotFoundException.class, () -> {
            challengeAnswerService.uploadAnswerPhoto(200L, challengeAnswerDTO);
        });
    }
}
