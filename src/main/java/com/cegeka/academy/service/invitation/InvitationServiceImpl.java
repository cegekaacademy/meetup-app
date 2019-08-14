package com.cegeka.academy.service.invitation;

import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.service.dto.InvitationDisplayDTO;
import com.cegeka.academy.service.util.InvitationMapper;
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

    private Logger logger =  LoggerFactory.getLogger(InvitationServiceImpl.class);

    @Autowired
    public InvitationServiceImpl(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    @Override
    public List<InvitationDisplayDTO> getAllInvitations() {

        List<InvitationDisplayDTO>listToShow = new ArrayList<>();
        List<Invitation>list = invitationRepository.findAll();
        for (Invitation invitation : list) {
            InvitationDisplayDTO aux = InvitationMapper.convertInvitationEntityToInvitationDisplayDTO(invitation);
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
}
