package com.cegeka.academy.service.invitation;

import com.cegeka.academy.domain.Invitation;

public interface InvitationService {
    String getAllInvitations();
    String saveInvitation(Invitation invitation);
    String updateInvitation(Invitation invitation);
    void deleteInvitationById(Long id);
}
