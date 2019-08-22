package com.cegeka.academy.service.invitation;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.GroupUserRole;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.repository.GroupUserRoleRepository;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.service.dto.InvitationDTO;
import com.cegeka.academy.service.mapper.InvitationMapper;
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
    private final GroupUserRoleRepository groupUserRoleRepository;

    private Logger logger =  LoggerFactory.getLogger(InvitationServiceImpl.class);

    @Autowired
    public InvitationServiceImpl(InvitationRepository invitationRepository, GroupUserRoleRepository groupUserRoleRepository) {
        this.invitationRepository = invitationRepository;
        this.groupUserRoleRepository = groupUserRoleRepository;
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

        logger.info("Invitation with id: "+ invitationRepository.save(invitation).getId() +"  was saved to database.");
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
    public void sendGroupInvitationsToPrivateEvents(Long idGroup, Event event) {

        List<GroupUserRole> listUsers = groupUserRoleRepository.findAllByGroupId(idGroup);
        //TODO
    }
}
