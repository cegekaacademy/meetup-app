package com.cegeka.academy.service.challengeAnswer;

import com.cegeka.academy.domain.ChallengeAnswer;
import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.repository.ChallengeAnswerRepository;
import com.cegeka.academy.repository.ChallengeRepository;
import com.cegeka.academy.repository.UserChallengeRepository;
import com.cegeka.academy.repository.UserRepository;
import com.cegeka.academy.service.dto.ChallengeAnswerDTO;
import com.cegeka.academy.service.mapper.ChallengeAnswerMapper;
import com.cegeka.academy.service.userChallenge.UserChallengeService;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;


@Service
@Transactional
public class ChallengeAnswerServiceImp implements ChallengeAnswerService {

    @Autowired
    private ChallengeAnswerRepository challengeAnswerRepository;
    @Autowired
    private UserChallengeRepository userChallengeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChallengeRepository challengeRepository;
    @Autowired
    private UserChallengeService userChallengeService;

    private Logger logger = LoggerFactory.getLogger(ChallengeAnswerServiceImp.class);
    private static String UPLOAD_ROOT_DIRECTORY = "C:\\photos\\";
    private static String UPLOAD_CHALLENGE_DIRECTORY = "challenge\\";
    private static String JPG_EXTENSION = ".jpg";

    @Override
    public void saveChallengeAnswer(ChallengeAnswerDTO challengeAnswerDTO) {

        ChallengeAnswer saveChallengeAnswer = ChallengeAnswerMapper.convertChallengeAnswerDTOToChallengeAnswer(challengeAnswerDTO);

        ChallengeAnswer result = challengeAnswerRepository.save(saveChallengeAnswer);

        logger.info("Challenge answer with id: " + result.getId() + " has been saved.");
    }

    @Override
    public void updateChallengeAnswer(Long id, ChallengeAnswerDTO challengeAnswerDTO) throws NotFoundException {

        ChallengeAnswer challengeAnswer = challengeAnswerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException().setMessage("Challenge answer not exists."));

        challengeAnswer.setAnswer(challengeAnswerDTO.getAnswer());
        challengeAnswer.setVideoAt(challengeAnswerDTO.getVideoAt());
        challengeAnswer.setImagePath(challengeAnswerDTO.getImagePath());

        ChallengeAnswer result = challengeAnswerRepository.save(challengeAnswer);

        logger.info("Challenge answer with id: " + result.getId() + " was updated.");

    }

    @Override
    public void deleteChallengeAnswer(Long userId, Long challengeId) throws NotFoundException {

        UserChallenge userChallenge = userChallengeRepository
                .findByUserIdAndChallengeId(userId, challengeId)
                .orElseThrow(() -> new NotFoundException().setMessage("User challenge not found"));

        if (userChallenge == null || userChallenge.getChallengeAnswer() == null) {

            throw new NotFoundException().setMessage("Challenge answer not exists.");
        }

        boolean isChallengeAnswerValid = challengeAnswerRepository.findById(userChallenge.getChallengeAnswer().getId()).isPresent();

        if (!isChallengeAnswerValid) {

            throw new NotFoundException().setMessage("Challenge answer not exists.");

        }

        ChallengeAnswer deleteChallengeAnswer = userChallenge.getChallengeAnswer();

        userChallenge.setChallengeAnswer(null);

        userChallengeRepository.save(userChallenge);

        challengeAnswerRepository.delete(deleteChallengeAnswer);

        logger.info("Challenge answer was deleted.");

    }

    @Override
    public void uploadAnswer(Long userId, Long challengeId, ChallengeAnswerDTO challengeAnswerDTO) throws IOException, NotFoundException {

        userRepository.findById(userId).orElseThrow(() -> new NotFoundException().setMessage("User not found"));
        challengeRepository.findById(challengeId).orElseThrow(() -> new NotFoundException().setMessage("Challenge not found"));
        UserChallenge userChallenge = userChallengeRepository.findByUserIdAndChallengeId(userId, challengeId)
                .orElseThrow(() -> new NotFoundException().setMessage("User challenge not found"));

        if (userChallenge.getChallengeAnswer() == null) {

            String imagePath = UPLOAD_ROOT_DIRECTORY + UPLOAD_CHALLENGE_DIRECTORY + userId + "\\" + challengeId + "\\";
            File file = new File(imagePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            byte[] bytes = challengeAnswerDTO.getImage().getBytes();
            String imageName = "answer_" + userId + "_" + challengeId + "_" + new Date().getTime();

            Path path = Paths.get(imagePath + imageName + JPG_EXTENSION);
            Files.write(path, bytes);

            challengeAnswerDTO.setImagePath(path.toString());

            ChallengeAnswer challengeAnswer = ChallengeAnswerMapper.convertChallengeAnswerDTOToChallengeAnswer(challengeAnswerDTO);

            challengeAnswerRepository.save(challengeAnswer);

            userChallengeService.updateUserChallengeAnswer(userChallenge, challengeAnswer);
        } else {
            throw new IllegalArgumentException("An answer has been given for this challenge");
        }
    }
}
