package com.cegeka.academy.service.invitation;

import com.cegeka.academy.domain.GroupUserRole;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.domain.enums.InvitationStatus;
import com.cegeka.academy.repository.EventRepository;
import com.cegeka.academy.repository.GroupUserRoleRepository;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.repository.UserRepository;
import com.cegeka.academy.service.dto.InvitationDTO;
import com.cegeka.academy.service.mapper.InvitationMapper;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final GroupUserRoleRepository groupUserRoleRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;


    private Logger logger =  LoggerFactory.getLogger(InvitationServiceImpl.class);

    @Autowired
    public InvitationServiceImpl(InvitationRepository invitationRepository, GroupUserRoleRepository groupUserRoleRepository,
                                 UserRepository userRepository, EventRepository eventRepository) {
        this.invitationRepository = invitationRepository;
        this.groupUserRoleRepository = groupUserRoleRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;

    }

    @Override
    public List<InvitationDTO> getAllInvitations() {

        List<InvitationDTO>listToShow = new ArrayList<>();
        List<Invitation>list = invitationRepository.findAll();
        for (Invitation invitation : list) {
            InvitationDTO aux = InvitationMapper.convertInvitationEntityToInvitationDTO(invitation);
            listToShow.add(aux);
        }

        return listToShow;
    }

    @Override
    public void saveInvitation(Invitation invitation) {

        invitation.setStatus(InvitationStatus.PENDING.name());
        logger.info("Invitation with id: "+ invitationRepository.save(invitation).getId() +"  was saved to database.");
        eventRepository.findById(invitation.getEvent().getId()).ifPresent(event -> {
            event.getPendingInvitations().add(invitation);
            eventRepository.save(event);
        });
    }

    @Override
    public void updateInvitation(Invitation invitation) {

        logger.info("Invitation with id: "+ invitationRepository.save(invitation).getId() +"  was updated into database.");

    }

    @Override
    public void deleteInvitationById(Long id) {

        invitationRepository.findById(id).ifPresent(invitation -> invitationRepository.delete(invitation));
    }

    @Override
    public void sendGroupInvitationsToPrivateEvents(Long idGroup, Invitation invitation) throws NotFoundException {

        if (invitation.getEvent() == null) {
            throw new NotFoundException();

        }
        if (!invitation.getEvent().isPublic()) {

            List<GroupUserRole> listIdUsers = groupUserRoleRepository.findAllByGroupId(idGroup);

            for (GroupUserRole userGroup : listIdUsers) {

                Optional<User> user = userRepository.findById(userGroup.getUser().getId());

                user.orElseThrow(NotFoundException::new);

                invitation.setUser(user.get());
                invitationRepository.save(invitation);

            }
        }
    }

    @Override
    public List<InvitationDTO> getPendingInvitationsByUserId(Long userId) {

        List<InvitationDTO> pendingInvitations = new ArrayList<>();
        List<Invitation> list = invitationRepository.findByUser_IdAndStatusIgnoreCase(userId, InvitationStatus.PENDING.name());
        for (Invitation invitation : list) {
            InvitationDTO aux = InvitationMapper.convertInvitationEntityToInvitationDTO(invitation);
            pendingInvitations.add(aux);
        }

        return pendingInvitations;
    }

    @Override
    public void acceptInvitation(Invitation invitation) {

        invitation.setStatus(InvitationStatus.ACCEPTED.name());
        logger.info("Invitation with id: " + invitationRepository.save(invitation).getId() + "  was accepted by the user.");
        eventRepository.findById(invitation.getEvent().getId()).ifPresent(event -> {
            event.getPendingInvitations().remove(invitation);
            event.getUsers().add(invitation.getUser());
            eventRepository.save(event);
        });


    }

    @Override
    public void rejectInvitation(Invitation invitation) {

        invitation.setStatus(InvitationStatus.REJECTED.name());
        logger.info("Invitation with id: " + invitationRepository.save(invitation).getId() + "  was rejected by the user.");

    }


}
