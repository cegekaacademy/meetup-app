package com.cegeka.academy.service.invitation;

import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.service.dto.InvitationDbDTO;
import com.cegeka.academy.service.dto.InvitationDisplayDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;

    private Logger logger =  LoggerFactory.getLogger(InvitationServiceImpl.class);

    @Autowired
    public InvitationServiceImpl(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    @Override
    public List<InvitationDisplayDTO> getAllInvitations() {

        List<InvitationDisplayDTO>listToShow = new ArrayList<>();
        List<Invitation>list = invitationRepository.findAll();
        for (Invitation invitation : list) {
            InvitationDisplayDTO aux = new InvitationDisplayDTO();
            aux.setDescription(invitation.getDescription());
            aux.setStatus(invitation.getStatus());
            aux.setUserName(invitation.getUser().getFirstName()+" "+invitation.getUser().getLastName());
            aux.setEventName(invitation.getEvent().getName());
            listToShow.add(aux);

        }

        return listToShow;
    }

    @Override
    public void saveInvitation(InvitationDbDTO invitation) {

        Invitation invitationAdded = new Invitation();
        invitationAdded.setStatus(invitation.getStatus());
        invitationAdded.setDescription(invitation.getDescription());
        invitationAdded.setEvent(invitation.getEvent());
        invitationAdded.setUser(invitation.getUser());

        logger.info("Invitation with id: "+ invitationRepository.saveAndFlush(invitationAdded).getId() +"  was saved to database.");
    }

    @Override
    public void updateInvitation(InvitationDbDTO invitation) {

        Invitation invitationUpdated = new Invitation();
        invitationUpdated.setId(invitation.getId());
        invitationUpdated.setStatus(invitation.getStatus());
        invitationUpdated.setDescription(invitation.getDescription());
        invitationUpdated.setEvent(invitation.getEvent());
        invitationUpdated.setUser(invitation.getUser());

        logger.info("Invitation with id: "+ invitationRepository.saveAndFlush(invitationUpdated).getId() +"  was updated into database.");
    }

    @Override
    public void deleteInvitationById(Long id) {

        invitationRepository.findById(id).ifPresent(invitation -> invitationRepository.delete(invitation));
        logger.info("Invitation with id: "+ id +"  was deleted from database.");
    }
}
