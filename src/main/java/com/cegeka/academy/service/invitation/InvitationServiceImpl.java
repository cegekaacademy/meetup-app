package com.cegeka.academy.service.invitation;

import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.repository.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvitationServiceImpl implements InvitationService {


    private final InvitationRepository invitationRepository;

    @Autowired
    public InvitationServiceImpl(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    @Override
    public String getAllInvitations() {
        return invitationRepository.findAll().toString();
    }

    @Override
    public String saveInvitation(Invitation invitation) {
        return "Invitation with id: " + invitationRepository.save(invitation).getId() + " was saved to h2 database.";
    }

    @Override
    public String updateInvitation(Invitation invitation) {
        invitationRepository.save(invitation);
        return "MODIFIED IT'S FAITH.";    }

    @Override
    public void deleteInvitationById(Long id) {
        invitationRepository.findById(id).ifPresent(invitation -> invitationRepository.delete(invitation));
    }
}
