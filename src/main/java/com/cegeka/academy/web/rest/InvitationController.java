package com.cegeka.academy.web.rest;

import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.service.dto.InvitationDTO;
import com.cegeka.academy.service.invitation.InvitationService;
import com.cegeka.academy.service.serviceValidation.ValidationAccessService;
import com.cegeka.academy.web.rest.errors.controllerException.ErrorResponse;
import com.cegeka.academy.web.rest.errors.controllerException.GeneralExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("invitation")
public class InvitationController {

    private final InvitationService invitationService;

    private final InvitationRepository invitationRepository;

    private final ValidationAccessService validationAccessService;

    @Autowired
    public InvitationController(InvitationService invitationService, ValidationAccessService validationAccessService, InvitationRepository invitationRepository) {
        this.invitationService = invitationService;
        this.validationAccessService = validationAccessService;
        this.invitationRepository = invitationRepository;
    }

    @GetMapping("/all")
    public List<InvitationDTO> getAllInvitations(){

        return invitationService.getAllInvitations();
    }

    @PostMapping
    public void saveInvitation(@RequestBody Invitation newInvitation){

         invitationService.saveInvitation(newInvitation);
    }

    @PutMapping
    public ResponseEntity<ErrorResponse> replaceInvitation(@RequestBody Invitation newInvitation) {

        if(validationAccessService.verifyUserAccessForInvitationEntity(newInvitation.getId())) {
            invitationService.updateInvitation(newInvitation);

        } else {

            return new GeneralExceptionHandler().handleUnauthorizedAccessInvitation(newInvitation);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorResponse> deleteInvitation(@PathVariable Long id) {

        Optional<Invitation> deleteInvitation = invitationRepository.findById(id);

        if (deleteInvitation.isPresent()) {

            if (validationAccessService.verifyUserAccessForInvitationEntity(id)) {

                invitationService.deleteInvitationById(id);

            } else {

                return new GeneralExceptionHandler().handleUnauthorizedAccessInvitation(deleteInvitation.get());
            }
        } else {

            return new GeneralExceptionHandler().handleNotFoundInvitation(id);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
