package com.cegeka.academy.domain.enums;

import com.cegeka.academy.web.rest.errors.InvalidUserChallengeStatusException;

public enum UserChallengeStatus {

    ACCEPTED, CANCELED;

    public static UserChallengeStatus getUserChallengeStatus(String status) throws InvalidUserChallengeStatusException {

        if(status.equalsIgnoreCase(ACCEPTED.toString())){

            return ACCEPTED;

        } else if(status.equalsIgnoreCase(CANCELED.toString())){

            return CANCELED;

        } else {

            throw new InvalidUserChallengeStatusException().setMessage("Status is invalid");
        }
    }
}
