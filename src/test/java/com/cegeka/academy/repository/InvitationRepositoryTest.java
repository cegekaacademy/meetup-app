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

    @Autowired
    InvitationRepository invitationRepository;

    @Test
    public void testInsert(){
        Invitation invitation = new Invitation();
        String description = "ana are mere";
        String status = "status";
        invitation.setDescription(description);
        invitation.setStatus(status);
        invitationRepository.save(invitation);
        assertThat(invitationRepository.findAll().size()).isEqualTo(1);
        assertThat(invitationRepository.findAll().get(0).getDescription()).isEqualTo(description);
        assertThat(invitationRepository.findAll().get(0).getStatus()).isEqualTo(status);
        //System.out.println(invitationRepository.getAllInvitations().get(0).toString());
    }

    @Test
    public void testGetByStatus(){
        Invitation invitation = new Invitation();
        String description = "ana are mere";
        String status = "status2";
        invitation.setDescription(description);
        invitation.setStatus(status);
        Invitation invitation2 = new Invitation();
        invitation2.setStatus("status2");
        invitation2.setDescription("ioana are pere");
        invitationRepository.save(invitation);
        invitationRepository.save(invitation2);
        List<Invitation> list = invitationRepository.getByStatus("status2");
        assertThat(list.get(0)).isEqualTo(invitationRepository.findAll().get(0));
        assertThat(list.get(1)).isEqualTo(invitationRepository.findAll().get(1));

    }

    @Test
    public void testGetByStatusWithNoData(){
        Invitation invitation = new Invitation();
        String description = "ana are mere";
        String status = "status2";
        invitation.setDescription(description);
        invitation.setStatus(status);
        Invitation invitation2 = new Invitation();
        invitation2.setStatus("status2");
        invitation2.setDescription("ioana are pere");
        invitationRepository.save(invitation);
        invitationRepository.save(invitation2);
        List<Invitation> list = invitationRepository.getByStatus("status1");
        assertThat(list.size()).isEqualTo(0);
    }
}
