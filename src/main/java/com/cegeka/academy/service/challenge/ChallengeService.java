package com.cegeka.academy.service.challenge;

import javassist.NotFoundException;

public interface ChallengeService {
    public void deleteChallenge(long id) throws NotFoundException;
}
