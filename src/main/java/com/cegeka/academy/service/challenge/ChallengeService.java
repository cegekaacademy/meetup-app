package com.cegeka.academy.service.challenge;

import com.cegeka.academy.service.dto.ChallengeDTO;
import com.cegeka.academy.web.rest.errors.NotFoundException;

import java.util.Set;

public interface ChallengeService {
    void deleteChallenge(long id) throws NotFoundException;

    void saveChallenge(ChallengeDTO challenge);

    ChallengeDTO updateChallenge(ChallengeDTO challengeDTO) throws NotFoundException;

    Set<ChallengeDTO> getChallengesByUserId(long id) throws NotFoundException;

    ChallengeDTO getChallengeById(long id) throws NotFoundException;
}
