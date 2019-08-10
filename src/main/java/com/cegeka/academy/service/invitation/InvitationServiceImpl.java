package com.cegeka.academy.service.invitation;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.EventRepository;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;


    private Logger logger =  LoggerFactory.getLogger(InvitationServiceImpl.class);


    @Autowired
    public InvitationServiceImpl(InvitationRepository invitationRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Invitation> getAllInvitations() {

        return invitationRepository.findAll();
    }

    @Override
    public void saveInvitation(Invitation invitation) {

        if(invitation.getUser() != null){

            User invitedUser = userRepository.findById(invitation.getUser().getId()).get();
            invitation.setUser(invitedUser);

        }

        if(invitation.getEvent() != null){

            Event invitationEvent = eventRepository.findById(invitation.getEvent().getId()).get();
            invitation.setEvent(invitationEvent);
        }
        logger.info("Invitation with id: "+ invitationRepository.save(invitation).getId() +"  was saved to database.");
    }

    @Override
    public void updateInvitation(Invitation invitation) {

        logger.info("Invitation with id: "+ invitationRepository.save(invitation).getId() +"  was updated into database.");
    }

    @Override
    public void deleteInvitationById(Long id) {

        invitationRepository.findById(id).ifPresent(invitation -> invitationRepository.delete(invitation));
        logger.info("Invitation with id: "+ id +"  was deleted from database.");
    }
}
