package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
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
        Invitation invitation = TestsRepositoryUtil.createInvitation("pending", "ana are mere", event, user);
        invitationRepository.save(invitation);
        System.out.println(user.getId()+"");
        List<Invitation> list = invitationRepository.findAll();
        System.out.println(list.get(0).getInvitedUser().getId()+"");
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getStatus()).isEqualTo(invitation.getStatus());
        assertThat(list.get(0).getDescription()).isEqualTo(invitation.getDescription());
        assertThat(list.get(0).getInvitationEvent().getId()).isEqualTo(invitation.getInvitationEvent().getId());
        assertThat(list.get(0).getInvitedUser().getId()).isEqualTo(invitation.getInvitedUser().getId());

    }

    @Test
    public void testFindByStatus(){
        Event event = TestsRepositoryUtil.createEvent(1234L, "Ana are mere!", "KFC Krushers Party", true);
        eventRepository.save(event);
        User user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        Invitation invitation1 = TestsRepositoryUtil.createInvitation("pending", "ana are mere", event, user);
        invitationRepository.save(invitation1);
        Invitation invitation2 = TestsRepositoryUtil.createInvitation("pending", "maria are pere", event, user);
        invitationRepository.save(invitation2);
        List<Invitation> list = invitationRepository.findByStatus("pending");
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void testFindByStatusWithNoResult(){
        Event event = TestsRepositoryUtil.createEvent(1234L, "Ana are mere!", "KFC Krushers Party", true);
        eventRepository.save(event);
        User user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        Invitation invitation1 = TestsRepositoryUtil.createInvitation("pending", "ana are mere", event, user);
        invitationRepository.save(invitation1);
        Invitation invitation2 = TestsRepositoryUtil.createInvitation("pending", "maria are pere", event, user);
        invitationRepository.save(invitation2);
        List<Invitation> list = invitationRepository.findByStatus("invited");
        assertThat(list.size()).isEqualTo(0);
    }
}
