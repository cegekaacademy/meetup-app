package com.cegeka.academy.service.userChallenge;

import com.cegeka.academy.domain.ChallengeAnswer;
import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.service.dto.ChallengeAnswerDTO;
import com.cegeka.academy.service.dto.UserChallengeDTO;
import com.cegeka.academy.web.rest.errors.InvalidInvitationStatusException;
import com.cegeka.academy.web.rest.errors.InvalidUserChallengeStatusException;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import com.cegeka.academy.web.rest.errors.WrongOwnerException;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public interface UserChallengeService {

    List<UserChallengeDTO> getUserChallengesByUserId(Long userId);

    void updateUserChallengeStatus(Long userChallengeId, String status) throws NotFoundException, InvalidUserChallengeStatusException;

    void updateUserChallengeInvitationStatus(Long userChallengeId, String status) throws NotFoundException, InvalidInvitationStatusException;

    UserChallenge rateUser(UserChallengeDTO userChallengeDTO, Long ownerId)
            throws WrongOwnerException, NoSuchElementException;

    void updateUserChallengeAnswer(UserChallenge userChallenge, ChallengeAnswer challengeAnswer);
}
