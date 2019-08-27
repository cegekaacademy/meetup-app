package com.cegeka.academy.service.userChallenge;

import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.repository.ChallengeRepository;
import com.cegeka.academy.repository.UserChallengeRepository;
import com.cegeka.academy.service.dto.*;
import com.cegeka.academy.service.mapper.UserChallengeMapper;
import com.cegeka.academy.web.rest.errors.WrongOwnerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
    public UserChallenge rateUser(UserChallengeDTO userChallengeDTO, Long ownerId)
            throws WrongOwnerException, NoSuchElementException {

        long userId = userChallengeDTO.getUser().getId();
        long challengeId = userChallengeDTO.getChallenge().getId();
        long invitationId = userChallengeDTO.getInvitation().getId();
        UserChallenge userChallenge = userChallengeRepository
                .findByUserIdAndChallengeIdAndInvitationId(userId, challengeId, invitationId);

        System.out.println(userChallenge);

        if (userChallenge != null) {
            userChallenge.setPoints(userChallengeDTO.getPoints());

            Optional<Challenge> challengeOptional = challengeRepository.findById(userChallenge.getChallenge().getId());

            Challenge challenge = challengeOptional.get();

            if (challenge.getCreator().getId().equals(ownerId)) {
                return userChallengeRepository.save(userChallenge);
            } else {
                throw new WrongOwnerException();
            }
        } else {
            throw new NoSuchElementException();
        }

    }
}
