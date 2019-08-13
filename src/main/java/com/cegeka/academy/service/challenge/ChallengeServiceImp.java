package com.cegeka.academy.service.challenge;

import com.cegeka.academy.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ChallengeServiceImp implements ChallengeService {

    @Autowired
    ChallengeRepository challengeRepository;

    @Override
    public void deleteChallenge(long id) {
        challengeRepository.deleteById(id);
    }
}
