package com.cegeka.academy.service.mapper;

import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.service.dto.InvitationDTO;

public class InvitationMapper {

    public static InvitationDTO convertInvitationEntityToInvitationDisplayDTO(Invitation invitation){

        InvitationDTO invitationDTO = new InvitationDTO();
        invitationDTO.setStatus(invitation.getStatus());
        invitationDTO.setDescription(invitation.getDescription());

        if(invitation.getEvent() != null){

              invitationDTO.setEventName(invitation.getEvent().getName());

        }

        if(invitation.getUser() != null){

            invitationDTO.setUserName(invitation.getUser().getFirstName()+" "+invitation.getUser().getLastName());

        }

        return invitationDTO;
    }
}
