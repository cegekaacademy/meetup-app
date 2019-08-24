package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Address;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.domain.enums.InvitationStatus;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class InvitationRepositoryTest {

    @Autowired
    private InvitationRepository invitationRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private UserRepository userRepository;
  
    @Autowired
    private AddressRepository addressRepository;

    private User user;
    private Event event;
    private Invitation invitation;
    private Address address;

    @BeforeEach
    public void init() {

        user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);
        event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
        eventRepository.saveAndFlush(event);
        invitation = TestsRepositoryUtil.createInvitation(InvitationStatus.PENDING.name(), "ana are mere", event, user);
        invitationRepository.save(invitation);
    }


    @Test
    public void testAddInvitation() {
        List<Invitation> list = invitationRepository.findAll();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getStatus()).isEqualTo(invitation.getStatus());
        assertThat(list.get(0).getDescription()).isEqualTo(invitation.getDescription());
        assertThat(list.get(0).getEvent().getId()).isEqualTo(invitation.getEvent().getId());
        assertThat(list.get(0).getUser().getId()).isEqualTo(invitation.getUser().getId());
    }

    @Test
    public void testFindByStatus() {

        Invitation invitation2 = TestsRepositoryUtil.createInvitation(InvitationStatus.PENDING.name(), "maria are pere", event, user);
        invitationRepository.save(invitation2);
        List<Invitation> list = invitationRepository.findByStatus(InvitationStatus.PENDING.name());
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void testFindByStatusWithNoResult() {

        Invitation invitation2 = TestsRepositoryUtil.createInvitation(InvitationStatus.PENDING.name(), "maria are pere", event, user);
        invitationRepository.save(invitation2);
        List<Invitation> list = invitationRepository.findByStatus(InvitationStatus.ACCEPTED.name());
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    public void testFindByUser_IdAndStatusIgnoreCase() {
        Invitation invitation2 = TestsRepositoryUtil.createInvitation(InvitationStatus.ACCEPTED.name(), "maria are pere", event, user);
        invitationRepository.save(invitation2);
        List<Invitation> list = invitationRepository.findByUser_IdAndStatusIgnoreCase(user.getId(), InvitationStatus.PENDING.name());
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    public void testFindByUser_IdAndStatusIgnoreCaseNoResult() {

        User user2 = TestsRepositoryUtil.createUser("login2", "bnaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user2);
        Invitation invitation2 = TestsRepositoryUtil.createInvitation(InvitationStatus.ACCEPTED.name(), "maria are pere", event, user2);
        invitationRepository.save(invitation2);
        List<Invitation> list = invitationRepository.findByUser_IdAndStatusIgnoreCase(user2.getId(), InvitationStatus.PENDING.name());
        assertThat(list.size()).isEqualTo(0);
    }
    @Test
    public void testFindByEventId() {
        User user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        Address address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);
        Event event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
        eventRepository.save(event);
        Invitation invitation1 = TestsRepositoryUtil.createInvitation("pending", "description1", event, user);
        invitationRepository.save(invitation1);
        event.getPendingInvitations().add(invitation1);
        eventRepository.save(event);
        Invitation invitation2 = TestsRepositoryUtil.createInvitation("pending", "description2", event, user);
        invitationRepository.save(invitation2);
        event.getPendingInvitations().add(invitation2);
        eventRepository.save(event);
        Set<Invitation> invitationList=invitationRepository.findAllByEvent_id(event.getId());
        assertThat(invitationList.size()).isEqualTo(2);

    }


}
