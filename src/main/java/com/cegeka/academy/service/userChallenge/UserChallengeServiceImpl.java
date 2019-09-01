package com.cegeka.academy.service.userChallenge;

import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.repository.UserChallengeRepository;
import com.cegeka.academy.service.dto.UserChallengeDTO;
import com.cegeka.academy.service.mapper.UserChallengeMapper;
import com.cegeka.academy.web.rest.errors.WrongOwnerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserChallengeServiceImpl implements UserChallengeService {

    @Autowired
    private UserChallengeRepository userChallengeRepository;

    @Override
    public List<UserChallengeDTO> getUserChallengesByUserId(Long userId) {

        List<UserChallenge> userChallengeList = userChallengeRepository.findAllByUserId(userId);

        return userChallengeList.stream().map(userChallenge -> UserChallengeMapper.convertUserChallengeToUserChallengeDTO(userChallenge)).collect(Collectors.toList());

    }

    @Override
    public UserChallenge rateUser(UserChallengeDTO userChallengeDTO, Long ownerId)
            throws WrongOwnerException {

        long userId = userChallengeDTO.getUser().getId();
        long challengeId = userChallengeDTO.getChallenge().getId();
        long invitationId = userChallengeDTO.getInvitation().getId();
        UserChallenge userChallenge = userChallengeRepository
                .findByUserIdAndChallengeIdAndInvitationId(userId, challengeId, invitationId).orElseThrow(NoSuchElementException::new);

        userChallenge.setPoints(userChallengeDTO.getPoints());

        if (userChallenge.getChallenge().getCreator().getId().equals(ownerId)) {
            return userChallengeRepository.save(userChallenge);
        } else {
            throw new WrongOwnerException();
        }
    }

    @Override
    public UserChallenge initUserChallenge(Challenge challenge, Invitation invitation) {

        UserChallenge userChallenge = new UserChallenge();

        userChallenge.setChallenge(challenge);
        userChallenge.setPoints(0);
        userChallenge.setUser(invitation.getUser());
        userChallenge.setInvitation(invitation);
        userChallenge.setStatus("Not started");
        userChallenge.setChallengeAnswer(null);
        userChallenge.setStartTime(new Date());
        userChallenge.setEndTime(new Date());

        return userChallengeRepository.save(userChallenge);
    }
}
