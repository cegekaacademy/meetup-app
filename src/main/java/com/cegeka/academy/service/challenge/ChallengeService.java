package com.cegeka.academy.service.challenge;

import javassist.NotFoundException;

import com.cegeka.academy.service.dto.ChallengeDTO;

public interface ChallengeService {
    public void deleteChallenge(long id) throws NotFoundException;

    void saveChallenge(ChallengeDTO challenge);
}
