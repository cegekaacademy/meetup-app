package com.cegeka.academy.service.mapper;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.service.dto.InvitationDTO;

public class InvitationMapper {

    public static InvitationDTO convertInvitationEntityToInvitationDTO(Invitation invitation) {

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

    public static Invitation createInvitation(String status, String descriere, User user, Event event) {
        Invitation invitation = new Invitation();
        invitation.setStatus(status);
        invitation.setDescription(descriere);
        invitation.setUser(user);
        invitation.setEvent(event);
        return invitation;
    }
}
