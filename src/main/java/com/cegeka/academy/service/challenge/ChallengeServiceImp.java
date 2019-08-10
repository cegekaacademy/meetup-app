package com.cegeka.academy.service.challenge;

import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.ChallengeRepository;
import com.cegeka.academy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ChallengeServiceImp implements ChallengeService {

    private ChallengeRepository challengeRepository;
    private UserRepository userRepository;

    @Autowired
    public ChallengeServiceImp(ChallengeRepository challengeRepository, UserRepository userRepository) {
        this.challengeRepository = challengeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String saveChallenge(Challenge challenge) {

        User user = userRepository.findById(challenge.getCreator().getId()).get();
        challenge.setCreator(user);
        return "Challenge with id: " + challengeRepository.saveAndFlush(challenge).getId() + " has been saved";
    }
}
