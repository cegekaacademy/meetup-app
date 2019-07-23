package com.cegeka.academy.service;

import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.repository.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InvitationService {

    @Autowired
    private InvitationRepository invitationRepository;

    public void addInvitation(Invitation invitation){
        invitationRepository.save(invitation);
    }

    public List<Invitation> getAllInvitations(){
       return invitationRepository.findAll();
    }

    public List<Invitation> getInvitationsByStatus(String status){
        return invitationRepository.getByStatus(status);
    }

}
