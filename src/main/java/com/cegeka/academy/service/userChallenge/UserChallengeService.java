package com.cegeka.academy.service.userChallenge;

import com.cegeka.academy.service.dto.UserChallengeDTO;

import java.util.List;

public interface UserChallengeService {

    List<UserChallengeDTO> getUserChallengesByUserId(Long userId);

    String rateUser(UserChallengeDTO userChallengeDTO, Long ownerId);
}
