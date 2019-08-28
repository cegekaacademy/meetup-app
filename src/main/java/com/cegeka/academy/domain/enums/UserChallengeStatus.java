package com.cegeka.academy.domain.enums;

import com.cegeka.academy.web.rest.errors.InvalidUserChallengeStatusException;

import java.util.Arrays;
import java.util.List;

public enum UserChallengeStatus {

    ACCEPTED, CANCELED;

    public static UserChallengeStatus getUserChallengeStatus(String status) throws InvalidUserChallengeStatusException {

        List<UserChallengeStatus> list  = Arrays.asList(UserChallengeStatus.values());

        return list.stream()
                .filter(userChallengeStatus -> status.equalsIgnoreCase(userChallengeStatus.toString()))
                .findFirst()
                .orElseThrow(() -> new InvalidUserChallengeStatusException().setMessage("Status is invalid"));
    }
}
