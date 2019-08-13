package com.cegeka.academy.service.util;

import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.service.dto.InvitationDbDTO;
import com.cegeka.academy.service.dto.InvitationDisplayDTO;

public class InvitationMapper {

    public static Invitation convertDTOtoInvitation(InvitationDbDTO invitationDbDTO){

        Invitation invitation = new Invitation();

        if(invitationDbDTO.getInvitation().getId() !=null){
            invitation.setId(invitationDbDTO.getInvitation().getId());
        }

        invitation.setStatus(invitationDbDTO.getInvitation().getStatus());
        invitation.setDescription(invitationDbDTO.getInvitation().getDescription());
        invitation.setUser(invitationDbDTO.getInvitation().getUser());
        invitation.setEvent(invitationDbDTO.getInvitation().getEvent());

        return invitation;

    }

    public static InvitationDisplayDTO convertInvitationEntityToInvitationDisplayDTO(Invitation invitation){

        InvitationDisplayDTO invitationDisplayDTO = new InvitationDisplayDTO();
        invitationDisplayDTO.setStatus(invitation.getStatus());
        invitationDisplayDTO.setDescription(invitation.getDescription());
        invitationDisplayDTO.setEventName(invitation.getEvent().getName());
        invitationDisplayDTO.setUserName(invitation.getUser().getFirstName()+" "+invitation.getUser().getLastName());
        return invitationDisplayDTO;
    }
}
