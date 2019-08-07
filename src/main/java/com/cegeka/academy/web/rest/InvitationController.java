package com.cegeka.academy.web.rest;

import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.service.invitation.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("invitation")
public class InvitationController {

    private final InvitationService invitationService;

    @Autowired
    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @GetMapping("/all")
    public String getAllInvitations(){
        return invitationService.getAllInvitations();
    }

    @PostMapping("/post")
    public String saveInvitation(@RequestBody Invitation newInvitation){
        return invitationService.saveInvitation(newInvitation);
    }

    @PutMapping("/update")
    public String replaceInvitation(@RequestBody Invitation newInvitation){
        return invitationService.updateInvitation(newInvitation);
    }

    @DeleteMapping("/invitation/{id}")
    void deleteInvitation(@PathVariable Long id) {
        invitationService.deleteInvitationById(id);
    }

}
