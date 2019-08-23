package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.*;
import com.cegeka.academy.domain.enums.InvitationStatus;
import com.cegeka.academy.repository.*;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import com.cegeka.academy.service.dto.InvitationDTO;
import com.cegeka.academy.service.invitation.InvitationService;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupUserRoleRepository groupUserRoleRepository;

    @Autowired
    private RoleRepository roleRepository;


    private User user, user1;
    private Event event, publicEvent, event2;
    private Invitation invitation, invitationSendToGroup, invitationWithNullEvent, invitationWithPublicEvent, invitation2, invitation3;
    private Address address;
    private Group group;
    private GroupUserRole groupUserRole1, groupUserRole2;
    private Role role;


    @BeforeEach
    public void init() {
        userRepository.deleteAll();
        user = TestsRepositoryUtil.createUser("login1", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        user1 = TestsRepositoryUtil.createUser("login2", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaaka");
        userRepository.save(user1);
        address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);
        event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", false, address, user);
        eventRepository.saveAndFlush(event);
        publicEvent = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
        eventRepository.saveAndFlush(publicEvent);
        invitation = TestsRepositoryUtil.createInvitation(InvitationStatus.PENDING.name(), "ana are mere", event, user);
        invitationService.saveInvitation(invitation);
        group = TestsRepositoryUtil.createGroup("gr1", "descriere grup");
        groupRepository.save(group);
        role = TestsRepositoryUtil.createRole("admin");
        roleRepository.save(role);
        groupUserRole1 = TestsRepositoryUtil.createGroupUserRole(userRepository.findAll().get(0), groupRepository.findAll().get(0), roleRepository.findAll().get(0));
        groupUserRoleRepository.save(groupUserRole1);
        groupUserRole2 = TestsRepositoryUtil.createGroupUserRole(userRepository.findAll().get(1), groupRepository.findAll().get(0), roleRepository.findAll().get(0));
        groupUserRoleRepository.save(groupUserRole2);
        invitationSendToGroup = TestsRepositoryUtil.createInvitation(InvitationStatus.PENDING.name(), "aaaa", eventRepository.findAll().get(0), null);
        invitationWithNullEvent = TestsRepositoryUtil.createInvitation(InvitationStatus.PENDING.name(), "aaaa", null, null);
        invitationWithPublicEvent = TestsRepositoryUtil.createInvitation(InvitationStatus.PENDING.name(), "aaaa", eventRepository.findAll().get(1), null);
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

    @Test
    public void assertThatSendGroupInvitationToPrivateEventIsWorking() throws NotFoundException {

        Long idGroup = groupRepository.findAll().get(0).getId();
        invitationService.sendGroupInvitationsToPrivateEvents(idGroup, invitationSendToGroup);
        List<Invitation> list = invitationRepository.findAll();
        Optional<User> findUser = userRepository.findById(list.get(list.lastIndexOf(invitationSendToGroup)).getUser().getId());
        Optional<Event> findEvent = eventRepository.findById(list.get(list.lastIndexOf(invitationSendToGroup)).getEvent().getId());

        assertThat(list.size()).isEqualTo(2);
        Assert.assertTrue(findUser.isPresent());
        Assert.assertTrue(findEvent.isPresent());
    }

    @Test
    public void assertThatSendGroupInvitationToPrivateEventIsWorkingWithNullIdGroup() throws NotFoundException {

        invitationService.sendGroupInvitationsToPrivateEvents(null, invitationSendToGroup);
        List<Invitation> list = invitationRepository.findAll();
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    public void assertThatSendGroupInvitationToPrivateEventIsWorkingWithInvalidIdGroup() throws NotFoundException {

        invitationService.sendGroupInvitationsToPrivateEvents(100L, invitationSendToGroup);
        List<Invitation> list = invitationRepository.findAll();
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    void assertThatSendGroupInvitationToPrivateEventThrowsNotFoundExceptionTest() {

        Assertions.assertThrows(NotFoundException.class, () -> invitationService.sendGroupInvitationsToPrivateEvents(1L, invitationWithNullEvent));
    }

    @Test
    public void assertThatSendGroupInvitationToPrivateEventIsNotWorkingWithPublicEvent() throws NotFoundException {

        Long idGroup = groupRepository.findAll().get(0).getId();
        invitationService.sendGroupInvitationsToPrivateEvents(idGroup, invitationWithPublicEvent);
        List<Invitation> list = invitationRepository.findAll();
        assertThat(list.size()).isEqualTo(1);
    }


}
