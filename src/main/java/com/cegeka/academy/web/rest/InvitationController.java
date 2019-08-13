package com.cegeka.academy.web.rest;

import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.service.dto.InvitationDbDTO;
import com.cegeka.academy.service.dto.InvitationDisplayDTO;
import com.cegeka.academy.service.invitation.InvitationService;
import com.cegeka.academy.service.invitation.ValidationAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("invitation")
public class InvitationController {

    private final InvitationService invitationService;

    private final ValidationAccessService validationAccessService;

    @Autowired
    public InvitationController(InvitationService invitationService, ValidationAccessService validationAccessService) {
        this.invitationService = invitationService;
        this.validationAccessService = validationAccessService;
    }

    @GetMapping("/all")
    public List<InvitationDisplayDTO> getAllInvitations(){

        return invitationService.getAllInvitations();
    }

    @PostMapping
    public void saveInvitation(@RequestBody InvitationDbDTO newInvitation){

         invitationService.saveInvitation(newInvitation);
    }

    @PutMapping
    public void replaceInvitation(@RequestBody InvitationDbDTO newInvitation){

        if(validationAccessService.verifyUserAccessForInvitationEntity(newInvitation.getInvitation().getId()))
        {
            invitationService.updateInvitation(newInvitation);

        }
    }

    @DeleteMapping("/{id}")
    void deleteInvitation(@PathVariable Long id) {

        if(validationAccessService.verifyUserAccessForInvitationEntity(id))
        {
            invitationService.deleteInvitationById(id);
        }
    }

}
