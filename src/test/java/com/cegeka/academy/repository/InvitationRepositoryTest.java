package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Invitation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class InvitationRepositoryTest {

    @Autowired InvitationRepository invitationRepository;

    @Test
    public void testAddInvitation(){
        Invitation invitation = new Invitation();
        invitation.setStatus("pending");
        invitation.setDescription("ana are mere");
        invitationRepository.save(invitation);
        List<Invitation> list = invitationRepository.findAll();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getStatus()).isEqualTo(invitation.getStatus());
        assertThat(list.get(0).getDescription()).isEqualTo(invitation.getDescription());

    }

    @Test
    public void testFindByStatus(){
        Invitation invitation1 = new Invitation();
        invitation1.setStatus("pending");
        invitation1.setDescription("ana are mere");
        invitationRepository.save(invitation1);
        Invitation invitation2 = new Invitation();
        invitation2.setStatus("pending");
        invitation2.setDescription("maria are pere");
        invitationRepository.save(invitation2);
        List<Invitation> list = invitationRepository.findByStatus("pending");
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void testFindByStatusWithNoResult(){
        Invitation invitation1 = new Invitation();
        invitation1.setStatus("pending");
        invitation1.setDescription("ana are mere");
        invitationRepository.save(invitation1);
        Invitation invitation2 = new Invitation();
        invitation2.setStatus("pending");
        invitation2.setDescription("maria are pere");
        invitationRepository.save(invitation2);
        List<Invitation> list = invitationRepository.findByStatus("invited");
        assertThat(list.size()).isEqualTo(0);
    }
}
