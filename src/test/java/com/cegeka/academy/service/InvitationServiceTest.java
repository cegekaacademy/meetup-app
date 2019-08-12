package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.EventRepository;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.repository.UserRepository;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import com.cegeka.academy.service.invitation.InvitationService;
import com.cegeka.academy.service.invitation.ValidationAccessService;
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
    InvitationRepository invitationRepository;
    private @Autowired
    EventRepository eventRepository;
    private @Autowired
    UserRepository userRepository;
    private @Autowired
    InvitationService invitationService;

    private User user;
    private Event event;
    private Invitation invitation;

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

        List<Invitation> list = invitationService.getAllInvitations();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getStatus()).isEqualTo(invitation.getStatus());
        assertThat(list.get(0).getDescription()).isEqualTo(invitation.getDescription());
        assertThat(list.get(0).getEvent().getId()).isEqualTo(invitation.getEvent().getId());
        assertThat(list.get(0).getUser().getId()).isEqualTo(invitation.getUser().getId());
    }

    @Test
    @Transactional
    public void assertThatUpdateInvitationIsWorking() {

        invitation.setStatus("am modificat status-ul");
        invitationService.updateInvitation(invitation);
        List<Invitation> list = invitationService.getAllInvitations();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getStatus()).isEqualTo(invitation.getStatus());
        assertThat(list.get(0).getDescription()).isEqualTo(invitation.getDescription());
        assertThat(list.get(0).getEvent().getId()).isEqualTo(invitation.getEvent().getId());
        assertThat(list.get(0).getUser().getId()).isEqualTo(invitation.getUser().getId());
    }

    @Test
    @Transactional
    public void assertThatDeleteInvitationIsWorking() {

        invitationService.deleteInvitationById(invitation.getId());
        List<Invitation> list = invitationService.getAllInvitations();
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void assertThatDeleteInvitationIsWorkingWithInvalidId() {

        invitationService.deleteInvitationById(100L);
        List<Invitation> list = invitationService.getAllInvitations();
        assertThat(list.size()).isEqualTo(1);
    }


}
