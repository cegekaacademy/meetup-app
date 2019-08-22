package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Address;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
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

    @Test
    public void testAddInvitation() {
        User user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        Address address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);
        Event event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
        eventRepository.save(event);
        Invitation invitation = TestsRepositoryUtil.createInvitation("pending", "ana are mere", event, user);
        invitationRepository.save(invitation);
        List<Invitation> list = invitationRepository.findAll();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getStatus()).isEqualTo(invitation.getStatus());
        assertThat(list.get(0).getDescription()).isEqualTo(invitation.getDescription());
        assertThat(list.get(0).getEvent().getId()).isEqualTo(invitation.getEvent().getId());
        assertThat(list.get(0).getUser().getId()).isEqualTo(invitation.getUser().getId());
    }

    @Test
    public void testFindByStatus() {
        User user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        Address address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);
        Event event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
        eventRepository.save(event);

        Invitation invitation1 = TestsRepositoryUtil.createInvitation("pending", "ana are mere", event, user);
        invitationRepository.save(invitation1);
        Invitation invitation2 = TestsRepositoryUtil.createInvitation("pending", "maria are pere", event, user);
        invitationRepository.save(invitation2);
        List<Invitation> list = invitationRepository.findByStatus("pending");
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void testFindByStatusWithNoResult() {
        User user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        Address address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);
        Event event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
        eventRepository.save(event);
        Invitation invitation1 = TestsRepositoryUtil.createInvitation("pending", "ana are mere", event, user);
        invitationRepository.save(invitation1);
        Invitation invitation2 = TestsRepositoryUtil.createInvitation("pending", "maria are pere", event, user);
        invitationRepository.save(invitation2);
        List<Invitation> list = invitationRepository.findByStatus("invited");
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
