package com.cegeka.academy.service.util;

import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.service.dto.InvitationDisplayDTO;

public class InvitationMapper {

    public static InvitationDisplayDTO convertInvitationEntityToInvitationDisplayDTO(Invitation invitation){

        InvitationDisplayDTO invitationDisplayDTO = new InvitationDisplayDTO();
        invitationDisplayDTO.setStatus(invitation.getStatus());
        invitationDisplayDTO.setDescription(invitation.getDescription());
        invitationDisplayDTO.setEventName(invitation.getEvent().getName());
        invitationDisplayDTO.setUserName(invitation.getUser().getFirstName()+" "+invitation.getUser().getLastName());
        return invitationDisplayDTO;
    }
}
