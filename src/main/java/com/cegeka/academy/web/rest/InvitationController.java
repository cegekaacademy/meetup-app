package com.cegeka.academy.web.rest;

import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.service.dto.InvitationDTO;
import com.cegeka.academy.service.invitation.InvitationService;
import com.cegeka.academy.service.serviceValidation.ValidationAccessService;
import com.cegeka.academy.web.rest.errors.ExistingItemException;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import com.cegeka.academy.web.rest.errors.UnauthorizedUserException;
import com.cegeka.academy.web.rest.strategy.InvitationConstants;
import com.cegeka.academy.web.rest.strategy.InvitationStrategy;
import org.springframework.beans.factory.BeanFactory;
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

    private final BeanFactory beanFactory;

    @Autowired
    public InvitationController(InvitationService invitationService, ValidationAccessService validationAccessService, InvitationRepository invitationRepository, BeanFactory bf) {
        this.invitationService = invitationService;
        this.validationAccessService = validationAccessService;
        this.invitationRepository = invitationRepository;
        this.beanFactory = bf;
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

                throw new UnauthorizedUserException().setMessage("You have no right to update this invitation");
            }

        } else {

            throw new NotFoundException().setMessage("Invitation not found");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteInvitation(@PathVariable Long id) throws NotFoundException {

        Optional<Invitation> deleteInvitation = invitationRepository.findById(id);

        if (deleteInvitation.isPresent()) {

            if (validationAccessService.verifyUserAccessForInvitationEntity(id)) {

                invitationService.deleteInvitationById(id);

            } else {

                throw new UnauthorizedUserException().setMessage("You have no right to delete this invitation");
            }
        } else {

            throw new NotFoundException().setMessage("Invitation not found");
        }
    }

    @GetMapping("/pending/{userId}")
    public List<InvitationDTO> getPendingInvitationsByUserId(@PathVariable Long userId) {

        return invitationService.getPendingInvitationsByUserId(userId);
    }

    @PutMapping("/{perform}/{id}")
    public void decideAboutInvitation(@PathVariable String perform, @PathVariable Long id) throws NotFoundException {
        switch (perform) {
            case InvitationConstants.ACCEPT_INVITATION:
                beanFactory.getBean(InvitationConstants.ACCEPT_INVITATION, InvitationStrategy.class).executeInvitation(id);
                break;
            case InvitationConstants.REJECT_INVITATION:
                beanFactory.getBean(InvitationConstants.REJECT_INVITATION, InvitationStrategy.class).executeInvitation(id);
                break;
            default:
                throw new NotFoundException();
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
