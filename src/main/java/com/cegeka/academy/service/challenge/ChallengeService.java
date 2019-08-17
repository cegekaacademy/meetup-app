package com.cegeka.academy.service.challenge;

import com.cegeka.academy.service.dto.ChallengeDTO;
import com.cegeka.academy.web.rest.errors.EmptyChallengeSetException;
import com.cegeka.academy.web.rest.errors.NotFoundException;

import java.util.Set;

public interface ChallengeService {
    void deleteChallenge(long id) throws NotFoundException;

    void saveChallenge(ChallengeDTO challenge);

    Set<ChallengeDTO> getChallengesByUserId(long id) throws EmptyChallengeSetException;
}
