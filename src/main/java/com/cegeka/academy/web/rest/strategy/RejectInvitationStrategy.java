package com.cegeka.academy.web.rest.strategy;

import com.cegeka.academy.service.invitation.InvitationService;
import com.cegeka.academy.service.serviceValidation.ValidationAccessService;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component(InvitationConstants.REJECT_INVITATION)
public class RejectInvitationStrategy implements InvitationStrategy {

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private ValidationAccessService validationAccessService;

    @Override
    public void executeInvitation(Long id) throws NotFoundException {

        if (validationAccessService.verifyUserAccessForInvitationEntity(id)) {
            invitationService.rejectInvitation(id);

        }
    }
}
