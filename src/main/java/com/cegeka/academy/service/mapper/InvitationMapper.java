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

    public static Invitation convertInvitationDTOToInvitation(InvitationDTO invitationDTO) {

        Invitation invitation = new Invitation();
        invitation.setStatus(invitationDTO.getStatus());
        invitation.setDescription(invitationDTO.getDescription());
        invitation.setEvent(null);

        if(invitationDTO.getUserId() != null){

            User newUser = new User();
            newUser.setId(invitationDTO.getUserId());
            invitation.setUser(newUser);

        }

        return invitation;
    }

    public static Invitation createInvitation(String description, String status, User user, Event event) {
        Invitation invitation = new Invitation();
        invitation.setStatus(status);
        invitation.setDescription(description);
        invitation.setUser(user);
        invitation.setEvent(event);
        return invitation;
    }
}
