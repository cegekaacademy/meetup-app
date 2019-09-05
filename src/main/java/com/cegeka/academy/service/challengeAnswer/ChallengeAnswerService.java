package com.cegeka.academy.service.challengeAnswer;

import com.cegeka.academy.service.dto.ChallengeAnswerDTO;
import com.cegeka.academy.web.rest.errors.NotFoundException;

import java.io.IOException;

public interface ChallengeAnswerService {

    void saveChallengeAnswer(ChallengeAnswerDTO challengeAnswerDTO);

    void updateChallengeAnswer(Long id, ChallengeAnswerDTO challengeAnswerDTO) throws NotFoundException;

    void deleteChallengeAnswer(Long userId, Long challengeId) throws NotFoundException;

    void uploadAnswerPhoto(Long challengeAnswerId, ChallengeAnswerDTO challengeAnswerDTO) throws IOException, NotFoundException;
}
