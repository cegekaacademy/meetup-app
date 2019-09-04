package com.cegeka.academy.web.rest;

import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.service.dto.InvitationDTO;
import com.cegeka.academy.service.invitation.InvitationService;
import com.cegeka.academy.service.serviceValidation.ValidationAccessService;
import com.cegeka.academy.web.rest.errors.ExistingItemException;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import com.cegeka.academy.web.rest.errors.UnauthorizedUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public void saveInvitation(@Valid @RequestBody Invitation newInvitation) {

         invitationService.saveInvitation(newInvitation);
    }

    @PutMapping
    public void replaceInvitation(@RequestBody Invitation newInvitation) throws NotFoundException {

        Optional<Invitation> updateInvitation = invitationRepository.findById(newInvitation.getId());

        if (updateInvitation.isPresent()) {

            if (validationAccessService.verifyUserAccessForInvitationEntity(newInvitation.getId())) {

                invitationService.updateInvitation(newInvitation);

            } else {

                throw new UnauthorizedUserException();
            }

        } else {

            throw new NotFoundException();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteInvitation(@PathVariable Long id) throws NotFoundException {

        Optional<Invitation> deleteInvitation = invitationRepository.findById(id);

        if (deleteInvitation.isPresent()) {

            if (validationAccessService.verifyUserAccessForInvitationEntity(id)) {

                invitationService.deleteInvitationById(id);

            } else {

                throw new UnauthorizedUserException();
            }
        } else {

            throw new NotFoundException();
        }
    }

    @GetMapping("/pending/{id}")
    public List<InvitationDTO> getPendingInvitationsByUserId(@PathVariable Long userId) {

        return invitationService.getPendingInvitationsByUserId(userId);
    }

    @PutMapping("/accept/{id}")
    public void acceptInvitation(@PathVariable Long id) throws NotFoundException {

        if (validationAccessService.verifyUserAccessForInvitationEntity(id)) {
            invitationService.acceptInvitation(id);

        }
    }

    @PutMapping("/reject/{id}")
    public void rejectInvitation(@PathVariable Long id) throws NotFoundException {

        if (validationAccessService.verifyUserAccessForInvitationEntity(id)) {
            invitationService.rejectInvitation(id);

        }
    }

    @PostMapping("/send/{idGroup}")
    public void sendGroupInvitationsToPrivateEvents(@PathVariable Long idGroup,
                                                    @Valid @RequestBody Invitation newInvitation) throws NotFoundException {

        invitationService.sendGroupInvitationsToPrivateEvents(idGroup, newInvitation);
    }

    @PostMapping("/challenge-invitation/{challengeId}")
    public Invitation createChallengeInvitationForOneUser(@RequestBody InvitationDTO invitationDTO,
                                                    @PathVariable Long challengeId) throws NotFoundException, ExistingItemException {

        return invitationService.createChallengeInvitationForOneUser(invitationDTO, challengeId);
    }

    @GetMapping("/{invitationId}")
    public Invitation getInvitation(@PathVariable Long invitationId) throws NotFoundException {

        return invitationService.getInvitation(invitationId);
    }
}
