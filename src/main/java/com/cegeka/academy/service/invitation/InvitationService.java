package com.cegeka.academy.service.invitation;

import com.cegeka.academy.domain.Invitation;

import java.util.List;

public interface InvitationService {

    List<Invitation> getAllInvitations();
    void saveInvitation(Invitation invitation);
    void updateInvitation(Invitation invitation);
    void deleteInvitationById(Long id);

}
