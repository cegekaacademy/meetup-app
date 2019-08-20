package com.cegeka.academy.service.userChallenge;

import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.repository.ChallengeRepository;
import com.cegeka.academy.repository.UserChallengeRepository;
import com.cegeka.academy.service.dto.*;
import com.cegeka.academy.service.mapper.UserChallengeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserChallengeServiceImpl implements UserChallengeService {

    @Autowired
    private UserChallengeRepository userChallengeRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Override
    public List<UserChallengeDTO> getUserChallengesByUserId(Long userId) {

        List<UserChallenge> userChallengeList = userChallengeRepository.findAllByUserId(userId);

        return userChallengeList.stream().map(userChallenge -> UserChallengeMapper.convertUserChallengeToUserChallengeDTO(userChallenge)).collect(Collectors.toList());

    }

    @Override
    public String rateUser(UserChallengeDTO userChallengeDTO, Long ownerId) {

        System.out.println(userChallengeDTO.toString());

        UserChallenge userChallenge = UserChallengeMapper.convertUserChallengeDTOToUserChallenge(userChallengeDTO);
        Challenge challenge = challengeRepository.findById(userChallenge.getChallenge().getId()).get();

        System.out.println(userChallenge.toString());

        if(challenge.getCreator().getId() == ownerId) {
            System.out.println("OKE");
            userChallengeRepository.saveAndFlush(userChallenge);
        }

        return "User with id " + userChallenge.getUser().getId() + " has been rated with " + userChallenge.getPoints() + " points by the owner of this challenge";
    }
}
