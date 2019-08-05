package com.cegeka.academy.repository.util;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.User;

public class InvitationUtil {

    public static Event createEvent(Long id, String description, String name, boolean isPublic){
        Event event = new Event();
        event.setAddressId(id);
        event.setDescription(description);
        event.setName(name);
        event.setPublic(isPublic);
        return event;
    }

    public static User createUser(String login, String password){
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }

    public static Invitation createInvitation(String status, String description, Event event, User user){
        Invitation invitation = new Invitation();
        invitation.setStatus(status);
        invitation.setDescription(description);
        invitation.setInvitedUser(user);
        invitation.setInvitationEvent(event);
        return invitation;
    }

}
