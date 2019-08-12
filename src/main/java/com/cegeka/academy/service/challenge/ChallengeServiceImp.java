package com.cegeka.academy.service.challenge;

import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.ChallengeCategoryRepository;
import com.cegeka.academy.repository.ChallengeRepository;
import com.cegeka.academy.repository.UserRepository;
import com.cegeka.academy.service.dto.ChallengeDTO;
import com.cegeka.academy.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ChallengeServiceImp implements ChallengeService {

    private ChallengeRepository challengeRepository;
    private UserRepository userRepository;
    private ChallengeCategoryRepository categoryRepository;

    @Autowired
    public ChallengeServiceImp(ChallengeRepository challengeRepository, UserRepository userRepository, ChallengeCategoryRepository categoryRepository) {
        this.challengeRepository = challengeRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String saveChallenge(ChallengeDTO challengeDTO) {

        Challenge challenge = new Challenge();

        challenge.setEndDate(challengeDTO.getEndDate());
        challenge.setStartDate(challengeDTO.getStartDate());
        challenge.setDescription(challengeDTO.getDescription());
        challenge.setPoints(challengeDTO.getPoints());
        challenge.setStatus(challengeDTO.getStatus());
        challenge.setEndDate(challengeDTO.getEndDate());
        if (challengeDTO.getCreator() != null) {
            challenge.setCreator(userRepository.findById(challengeDTO.getCreator().getId()).get());
        }
        if(challengeDTO.getChallengeCategory() != null) {
            challenge.setChallengeCategory(categoryRepository.findByName(challengeDTO.getChallengeCategory().getName()));
        }

        return "Challenge with id: " + challengeRepository.saveAndFlush(challenge).getId() + " has been saved";
    }
}
