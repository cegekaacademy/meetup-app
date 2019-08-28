package com.cegeka.academy.domain.enums;

import com.cegeka.academy.web.rest.errors.InvalidInvitationStatusException;

public enum InvitationStatus {

    PENDING, ACCEPTED, REJECTED, CANCELED;

    public static InvitationStatus getInvitationStatus(String status) throws InvalidInvitationStatusException {

        if(status.equalsIgnoreCase(PENDING.toString())){

            return PENDING;

        } else if (status.equalsIgnoreCase(ACCEPTED.toString())){

            return ACCEPTED;

        } else if(status.equalsIgnoreCase(REJECTED.toString())){

            return REJECTED;

        } else if(status.equalsIgnoreCase(CANCELED.toString())){

            return CANCELED;

        } else {

            throw new InvalidInvitationStatusException().setMessage("Status is invalid");
        }
    }
}
