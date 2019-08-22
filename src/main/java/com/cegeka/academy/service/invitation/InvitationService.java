package com.cegeka.academy.service.invitation;

import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.service.dto.InvitationDTO;

import java.util.List;

public interface InvitationService {

    List<InvitationDTO> getAllInvitations();
    void saveInvitation(Invitation invitation);
    void updateInvitation(Invitation invitation);
    void deleteInvitationById(Long id);

    List<InvitationDTO> getPendingInvitationsByUserId(Long userId);

    void acceptInvitation(Invitation invitation);

    void rejectInvitation(Invitation invitation);

}
