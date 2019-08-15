package com.cegeka.academy.service.invitation;

import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.service.dto.InvitationDisplayDTO;

import java.util.List;

public interface InvitationService {

    List<InvitationDisplayDTO> getAllInvitations();
    void saveInvitation(Invitation invitation);
    void updateInvitation(Invitation invitation);
    void deleteInvitationById(Long id);

}
