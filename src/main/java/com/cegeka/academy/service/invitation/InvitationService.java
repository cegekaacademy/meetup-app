package com.cegeka.academy.service.invitation;

import com.cegeka.academy.service.dto.InvitationDbDTO;
import com.cegeka.academy.service.dto.InvitationDisplayDTO;

import java.util.List;

public interface InvitationService {

    List<InvitationDisplayDTO> getAllInvitations();
    void saveInvitation(InvitationDbDTO invitation);
    void updateInvitation(InvitationDbDTO invitation);
    void deleteInvitationById(Long id);

}
