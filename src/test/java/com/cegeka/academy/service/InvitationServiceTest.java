package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.EventRepository;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.repository.UserRepository;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import com.cegeka.academy.service.dto.InvitationDbDTO;
import com.cegeka.academy.service.dto.InvitationDisplayDTO;
import com.cegeka.academy.service.invitation.InvitationService;
import com.cegeka.academy.service.invitation.ValidationAccessService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class InvitationServiceTest {

    private @Autowired
    EventRepository eventRepository;
    private @Autowired
    UserRepository userRepository;
    private @Autowired
    InvitationService invitationService;
    private @Autowired
    InvitationRepository invitationRepository;

    private User user;
    private Event event;
    private InvitationDbDTO invitation;

    @BeforeEach
    public void init() {

        user = TestsRepositoryUtil.createUser("login","anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.saveAndFlush(user);
        event = TestsRepositoryUtil.createEvent(1234L, "Ana are mere!", "KFC Krushers Party", true);
        eventRepository.saveAndFlush(event);
        invitation = TestsRepositoryUtil.createInvitation("pending", "ana are mere", event, user);
        invitationService.saveInvitation(invitation);
    }

    @Test
    @Transactional
    public void assertThatSaveInvitationIsWorking() {

        List<InvitationDisplayDTO> list = invitationService.getAllInvitations();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getStatus()).isEqualTo(invitation.getInvitation().getStatus());
        assertThat(list.get(0).getDescription()).isEqualTo(invitation.getInvitation().getDescription());
        assertThat(list.get(0).getUserName()).isEqualTo(invitation.getInvitation().getUser().getFirstName()+" "+invitation.getInvitation().getUser().getLastName());
        assertThat(list.get(0).getEventName()).isEqualTo(invitation.getInvitation().getEvent().getName());
    }

    @Test
    @Transactional
    public void assertThatUpdateInvitationIsWorking() {
        List<Invitation> list = invitationRepository.findAll();
        invitation.getInvitation().setStatus("am modificat status-ul");
        invitation.getInvitation().setId(list.get(0).getId());
        invitationService.updateInvitation(invitation);
        System.out.println(invitation.getInvitation().getId()+"");
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getStatus()).isEqualTo(invitation.getInvitation().getStatus());
        assertThat(list.get(0).getDescription()).isEqualTo(invitation.getInvitation().getDescription());
        assertThat(list.get(0).getUser()).isEqualTo(invitation.getInvitation().getUser());
        assertThat(list.get(0).getEvent()).isEqualTo(invitation.getInvitation().getEvent());
    }

    @Test
    @Transactional
    public void assertThatDeleteInvitationIsWorking() {

        List<Invitation> listBeforeDelete = invitationRepository.findAll();
        invitationService.deleteInvitationById(listBeforeDelete.get(0).getId());
        List<Invitation> listAfterDelete = invitationRepository.findAll();
        assertThat(listAfterDelete.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void assertThatDeleteInvitationIsWorkingWithInvalidId() {

        invitationService.deleteInvitationById(100L);
        List<InvitationDisplayDTO> list = invitationService.getAllInvitations();
        assertThat(list.size()).isEqualTo(1);
    }


}
