package com.cegeka.academy.service.invitation;

import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.EventRepository;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ValidationAccessService {

    private final UserService userService;
    private final InvitationRepository invitationRepository;
    private final EventRepository eventRepository;

    private Logger logger =  LoggerFactory.getLogger(InvitationServiceImpl.class);


    @Autowired
    public ValidationAccessService(UserService userService, InvitationRepository invitationRepository, EventRepository eventRepository) {
        this.userService = userService;
        this.invitationRepository = invitationRepository;
        this.eventRepository = eventRepository;
    }

    public boolean verifyUserAccessForInvitationEntity(Long invitationId){

        if(invitationId == null){

            return false;

        }else {

            if (userService.getUserWithAuthorities().isPresent()) {

                User userLogged = userService.getUserWithAuthorities().get();

                if(invitationRepository.findById(invitationId).isPresent()) {

                    User invitedUser = invitationRepository.findById(invitationId).get().getUser();

                    if (invitedUser == null || userLogged == null || userLogged.getId() != invitedUser.getId()) {

                        return false;
                    }
                }

            }else{

                return false;
            }

          }

        return true;
    }

    public boolean verifyUserAccessForEventEntity(Long eventId) {
        if (eventId == null)
            return false;
        if (userService.getUserWithAuthorities().isPresent()) {

            User userLogged = userService.getUserWithAuthorities().get();

            if (eventRepository.findById(eventId).isPresent()) {

                User eventOwner = eventRepository.findById(eventId).get().getOwner();

                return eventOwner != null && userLogged.getId() == eventOwner.getId();
            }

        } else {

            return false;
        }
        return true;
    }
}
