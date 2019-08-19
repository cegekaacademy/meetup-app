package com.cegeka.academy.service.challengeAnswer;

import com.cegeka.academy.domain.ChallengeAnswer;
import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.repository.ChallengeAnswerRepository;
import com.cegeka.academy.repository.UserChallengeRepository;
import com.cegeka.academy.service.dto.ChallengeAnswerDTO;
import com.cegeka.academy.service.mapper.ChallengeAnswerMapper;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@Transactional
public class ChallengeAnswerServiceImp implements ChallengeAnswerService {

    @Autowired
    private ChallengeAnswerRepository challengeAnswerRepository;
    @Autowired
    private UserChallengeRepository userChallengeRepository;

    private Logger logger =  LoggerFactory.getLogger(ChallengeAnswerServiceImp.class);

    @Override
    public void saveChallengeAnswer(ChallengeAnswerDTO challengeAnswerDTO) {

        Long id = challengeAnswerRepository.save(ChallengeAnswerMapper.convertChallengeAnswerDTOToChallengeAnswer(challengeAnswerDTO)).getId();

        logger.info("Challenge answer with id: " + id + " has been saved.");
    }

    @Override
    public void updateChallengeAnswer(Long id, ChallengeAnswerDTO challengeAnswerDTO) throws NotFoundException {


        ChallengeAnswer challengeAnswer = challengeAnswerRepository.findById(id).orElseThrow(()-> new NotFoundException().setMessage("Challenge answer not exists."));

        challengeAnswer.setAnswer(challengeAnswerDTO.getAnswer());
        challengeAnswer.setVideoAt(challengeAnswerDTO.getVideoAt());
        challengeAnswer.setImagePath(challengeAnswerDTO.getImagePath());

        Long updateChallengeAnswerId =  challengeAnswerRepository.save(ChallengeAnswerMapper.convertChallengeAnswerDTOToChallengeAnswer(ChallengeAnswerMapper.convertChallengeAnswerToChallengeAnswerDTO(challengeAnswer))).getId();

        logger.info("Challenge answer with id: " + updateChallengeAnswerId + " was updated.");

    }

    @Override
    public void deleteChallengeAnswer(Long userId, Long challengeId) throws NotFoundException {

        UserChallenge userChallenge = userChallengeRepository.findAllByUserIdAndChallengeId(userId, challengeId);

        if (userChallenge == null || userChallenge.getChallengeAnswer() == null || !challengeAnswerRepository.findById(userChallenge.getChallengeAnswer().getId()).isPresent()){

            throw new NotFoundException().setMessage("Challenge answer not exists.");
        }

        ChallengeAnswer deleteChallengeAnswer = userChallenge.getChallengeAnswer();

        userChallenge.setChallengeAnswer(null);

        userChallengeRepository.save(userChallenge);

        challengeAnswerRepository.delete(deleteChallengeAnswer);

        logger.info("Challenge answer was deleted.");

    }
}
