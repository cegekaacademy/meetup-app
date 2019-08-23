package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Address;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.domain.enums.InvitationStatus;
import com.cegeka.academy.repository.AddressRepository;
import com.cegeka.academy.repository.EventRepository;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.repository.UserRepository;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import com.cegeka.academy.service.dto.InvitationDTO;
import com.cegeka.academy.service.invitation.InvitationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class InvitationServiceTest {


    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private AddressRepository addressRepository;

    private User user;
    private Event event,event2;
    private Invitation invitation,invitation2,invitation3;
    private Address address;

    @BeforeEach
    public void init() {

        user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.saveAndFlush(user);
        address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);
        event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
        eventRepository.saveAndFlush(event);
        invitation = TestsRepositoryUtil.createInvitation(InvitationStatus.PENDING.name(), "ana are mere", event, user);
        invitationService.saveInvitation(invitation);

    }
    @Test
    @Transactional
    public void assertThatSaveInvitationIsWorking() {

        List<InvitationDTO> list = invitationService.getAllInvitations();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getStatus()).isEqualTo(invitation.getStatus());
        assertThat(list.get(0).getStatus()).isEqualTo(InvitationStatus.PENDING.name());
        assertThat(list.get(0).getDescription()).isEqualTo(invitation.getDescription());
        assertThat(list.get(0).getUserName()).isEqualTo(invitation.getUser().getFirstName() + " " + invitation.getUser().getLastName());
        assertThat(list.get(0).getEventName()).isEqualTo(invitation.getEvent().getName());
    }
    @Test
    @Transactional
    public void assertThatSaveInvitationToListIsWorking() {

        assertThat(event.getPendingInvitations().size()).isEqualTo(1);
        invitation2 = TestsRepositoryUtil.createInvitation(InvitationStatus.PENDING.name(), "ana are mere", event, user);
        invitationService.saveInvitation(invitation2);
        assertThat(event.getPendingInvitations().size()).isEqualTo(2);

    }
    @Test
    @Transactional
    public void assertThatSaveUserToParticipationListAfterAcceptInvitationIsWorking() {
        event2 = TestsRepositoryUtil.createEvent("Dana Dana!", "KFC Krushers Party", true, address, user);
        eventRepository.save(event2);
        invitation3 = TestsRepositoryUtil.createInvitation(InvitationStatus.PENDING.name(), "ana are mere", event2, user);
        invitationService.saveInvitation(invitation3);
        invitationService.acceptInvitation(invitation3);
        assertThat(event2.getUsers().size()).isEqualTo(1);
        assertThat(event.getUsers().size()).isEqualTo(0);

    }
    @Test
    @Transactional
    public void assertThatUpdateInvitationIsWorking() {
        List<Invitation> list = invitationRepository.findAll();
        invitation.setStatus(InvitationStatus.ACCEPTED.name());
        invitation.setId(list.get(0).getId());
        invitationService.updateInvitation(invitation);
        System.out.println(invitation.getId() + "");
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getStatus()).isEqualTo(invitation.getStatus());
        assertThat(list.get(0).getDescription()).isEqualTo(invitation.getDescription());
        assertThat(list.get(0).getUser()).isEqualTo(invitation.getUser());
        assertThat(list.get(0).getEvent()).isEqualTo(invitation.getEvent());
    }

    @Test
    @Transactional
    public void assertThatDeleteInvitationIsWorking() {

        List<Invitation> listBeforeDelete = invitationRepository.findAll();
        invitationService.deleteInvitationById(listBeforeDelete.get(0).getId());
        List<Invitation> listAfterDelete = invitationRepository.findAll();
        assertThat(listAfterDelete.size()).isEqualTo(0);
    }

    @Test()
    @Transactional
    public void assertThatDeleteInvitationIsWorkingWithInvalidId() {

        invitationService.deleteInvitationById(100L);
        List<InvitationDTO> list = invitationService.getAllInvitations();
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    public void assertThatGetPendingInvitationsByUserIdIsWorking() {
        List<InvitationDTO> pendingListUser = invitationService.getPendingInvitationsByUserId(user.getId());
        assertThat(pendingListUser.size()).isEqualTo(1);

    }

    @Test
    public void assertThatAcceptInvitationIsWorking() {
        List<Invitation> list = invitationRepository.findAll();
        invitationService.acceptInvitation(invitation);
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getStatus()).isEqualTo(invitation.getStatus());
        assertThat(list.get(0).getStatus()).isEqualTo(InvitationStatus.ACCEPTED.name());
        assertThat(list.get(0).getDescription()).isEqualTo(invitation.getDescription());
        assertThat(list.get(0).getUser()).isEqualTo(invitation.getUser());
        assertThat(list.get(0).getEvent()).isEqualTo(invitation.getEvent());
        assertThat(event.getPendingInvitations().size()).isEqualTo(0);
        assertThat(event.getUsers().size()).isEqualTo(1);
    }

    @Test
    public void assertThatRejectInvitationIsWorking() {
        List<Invitation> list = invitationRepository.findAll();
        invitation.setStatus(InvitationStatus.PENDING.name());
        invitationService.rejectInvitation(invitation);
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getStatus()).isEqualTo(invitation.getStatus());
        assertThat(list.get(0).getStatus()).isEqualTo(InvitationStatus.REJECTED.name());
        assertThat(list.get(0).getDescription()).isEqualTo(invitation.getDescription());
        assertThat(list.get(0).getUser()).isEqualTo(invitation.getUser());
        assertThat(list.get(0).getEvent()).isEqualTo(invitation.getEvent());
    }



}
