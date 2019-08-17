package com.cegeka.academy.service.challenge;

import com.cegeka.academy.service.dto.ChallengeDTO;
import com.cegeka.academy.web.rest.errors.NotFoundException;

public interface ChallengeService {
    public void deleteChallenge(long id) throws NotFoundException;

    void saveChallenge(ChallengeDTO challenge);
}
