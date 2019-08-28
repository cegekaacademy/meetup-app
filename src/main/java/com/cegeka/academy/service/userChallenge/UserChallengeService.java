package com.cegeka.academy.service.userChallenge;

import com.cegeka.academy.service.dto.UserChallengeDTO;
import com.cegeka.academy.web.rest.errors.InvalidUserChallengeStatusException;
import com.cegeka.academy.web.rest.errors.NotFoundException;

import java.util.List;

public interface UserChallengeService {

    List<UserChallengeDTO> getUserChallengesByUserId(Long userId);

    void updateUserChallengeStatus(Long userChallengeId, String status) throws NotFoundException, InvalidUserChallengeStatusException;

    void updateUserChallengeInvitationStatus(Long userChallengeId, String status) throws NotFoundException, InvalidUserChallengeStatusException;

}
