package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import com.cegeka.academy.service.dto.InvitationDbDTO;
import com.cegeka.academy.service.dto.InvitationDisplayDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class InvitationRepositoryTest {

    private @Autowired InvitationRepository invitationRepository;
    private @Autowired EventRepository eventRepository;
    private @Autowired UserRepository userRepository;

    @Test
    public void testAddInvitation(){
        Event event = TestsRepositoryUtil.createEvent(1234L, "Ana are mere!", "KFC Krushers Party", true);
        eventRepository.save(event);
        User user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        InvitationDbDTO invitation = TestsRepositoryUtil.createInvitation("pending", "ana are mere", event, user);
        invitationRepository.save(TestsRepositoryUtil.convertDTOToInvitation(invitation));
        List<Invitation> list = invitationRepository.findAll();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getStatus()).isEqualTo(invitation.getInvitation().getStatus());
        assertThat(list.get(0).getDescription()).isEqualTo(invitation.getInvitation().getDescription());
        assertThat(list.get(0).getEvent().getId()).isEqualTo(invitation.getInvitation().getEvent().getId());
        assertThat(list.get(0).getUser().getId()).isEqualTo(invitation.getInvitation().getUser().getId());
    }

    @Test
    public void testFindByStatus(){
        Event event = TestsRepositoryUtil.createEvent(1234L, "Ana are mere!", "KFC Krushers Party", true);
        eventRepository.save(event);
        User user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        InvitationDbDTO invitation1 = TestsRepositoryUtil.createInvitation("pending", "ana are mere", event, user);
        invitationRepository.save(TestsRepositoryUtil.convertDTOToInvitation(invitation1));
        InvitationDbDTO invitation2 = TestsRepositoryUtil.createInvitation("pending", "maria are pere", event, user);
        invitationRepository.save(TestsRepositoryUtil.convertDTOToInvitation(invitation2));
        List<Invitation> list = invitationRepository.findByStatus("pending");
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void testFindByStatusWithNoResult(){
        Event event = TestsRepositoryUtil.createEvent(1234L, "Ana are mere!", "KFC Krushers Party", true);
        eventRepository.save(event);
        User user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        InvitationDbDTO invitation1 = TestsRepositoryUtil.createInvitation("pending", "ana are mere", event, user);
        invitationRepository.save(TestsRepositoryUtil.convertDTOToInvitation(invitation1));
        InvitationDbDTO invitation2 = TestsRepositoryUtil.createInvitation("pending", "maria are pere", event, user);
        invitationRepository.save(TestsRepositoryUtil.convertDTOToInvitation(invitation2));
        List<Invitation> list = invitationRepository.findByStatus("invited");
        assertThat(list.size()).isEqualTo(0);
    }
}
