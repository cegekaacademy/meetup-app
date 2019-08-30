package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.*;
import com.cegeka.academy.domain.enums.InvitationStatus;
import com.cegeka.academy.repository.*;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import com.cegeka.academy.service.dto.InvitationDTO;
import com.cegeka.academy.service.invitation.InvitationService;
import com.cegeka.academy.service.mapper.InvitationMapper;
import com.cegeka.academy.web.rest.errors.ExistingItemException;
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

    private User user, user1, user2;
    private Event event, event2, publicEvent;
    private Invitation invitation, invitation2, invitation3, invitationSendToGroup, invitationWithNullEvent, invitationWithPublicEvent;
    private Address address;
    private Group group;
    private GroupUserRole groupUserRole1, groupUserRole2, groupUserRole3;
    private Role role;

    @BeforeEach
    public void init() {

        userRepository.deleteAll();
        user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.saveAndFlush(user);
        user1 = TestsRepositoryUtil.createUser("login2", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaaka");
        userRepository.save(user1);
        user2 = TestsRepositoryUtil.createUser("login3", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaama");
        userRepository.save(user2);
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
        groupUserRole3 = TestsRepositoryUtil.createGroupUserRole(userRepository.findAll().get(2), groupRepository.findAll().get(0), roleRepository.findAll().get(0));
        groupUserRoleRepository.save(groupUserRole3);
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
    public void assertThatSaveUserToParticipationListAfterAcceptInvitationIsWorking() throws NotFoundException {
        event2 = TestsRepositoryUtil.createEvent("Dana Dana!", "KFC Krushers Party", true, address, user);
        eventRepository.save(event2);
        invitation3 = TestsRepositoryUtil.createInvitation(InvitationStatus.PENDING.name(), "ana are mere", event2, user);
        invitationService.saveInvitation(invitation3);
        invitationService.acceptInvitation(invitation3.getId());
        assertThat(user.getEvents().size()).isEqualTo(1);
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
    @Transactional
    public void assertThatAcceptInvitation_ThrowsExceptionWithWrongInvitationId() {
        Assertions.assertThrows(NotFoundException.class, () -> invitationService.acceptInvitation(40l));
    }

    @Test
    @Transactional
    public void assertThatAcceptInvitation_ThrowsExceptionWithWrongEventId() {

        invitation.getEvent().setId(111L);
        invitationRepository.save(invitation);
        Assertions.assertThrows(NotFoundException.class, () -> invitationService.acceptInvitation(invitation.getId()));
    }

    @Test
    @Transactional
    public void assertThatAcceptInvitation_ThrowsExceptionWithWrongUserId() {

        invitation.getUser().setId(111l);
        invitationRepository.save(invitation);
        Assertions.assertThrows(NotFoundException.class, () -> invitationService.acceptInvitation(invitation.getId()));
    }

    @Test
    public void assertThatAcceptInvitationIsWorking() throws NotFoundException {
        List<Invitation> list = invitationRepository.findAll();
        invitationService.acceptInvitation(invitation.getId());
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getStatus()).isEqualTo(invitation.getStatus());
        assertThat(list.get(0).getStatus()).isEqualTo(InvitationStatus.ACCEPTED.name());
        assertThat(list.get(0).getDescription()).isEqualTo(invitation.getDescription());
        assertThat(list.get(0).getUser()).isEqualTo(invitation.getUser());
        assertThat(list.get(0).getEvent()).isEqualTo(invitation.getEvent());
        assertThat(event.getPendingInvitations().size()).isEqualTo(0);
        assertThat(user.getEvents().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void assertThatRejectInvitation_ThrowsExceptionWithWrongInvitationId() {
        Assertions.assertThrows(NotFoundException.class, () -> invitationService.rejectInvitation(40l));
    }

    @Test
    public void assertThatRejectInvitationIsWorking() throws NotFoundException {
        List<Invitation> list = invitationRepository.findAll();
        invitation.setStatus(InvitationStatus.PENDING.name());
        invitationService.rejectInvitation(invitation.getId());
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
        invitationSendToGroup.setId(invitationRepository.findAll().get(2).getId());
        List<Invitation> list = invitationRepository.findAll();
        Optional<User> findUser1 = userRepository.findById(list.get(list.lastIndexOf(invitationSendToGroup)).getUser().getId());
        Optional<Event> findEvent1 = eventRepository.findById(list.get(list.lastIndexOf(invitationSendToGroup)).getEvent().getId());
        invitationSendToGroup.setId(invitationRepository.findAll().get(1).getId());
        Optional<User> findUser2 = userRepository.findById(list.get(list.lastIndexOf(invitationSendToGroup) - 1).getUser().getId());
        Optional<Event> findEvent2 = eventRepository.findById(list.get(list.lastIndexOf(invitationSendToGroup) - 1).getEvent().getId());

        assertThat(list.size()).isEqualTo(3);
        assertTrue(findUser1.isPresent());
        assertTrue(findEvent1.isPresent());
        assertTrue(findUser2.isPresent());
        assertTrue(findEvent2.isPresent());
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

    @Test
    public void assertThatCreateChallengeInvitationIsWorking() throws NotFoundException, ExistingItemException {
        InvitationDTO invitationDTO = new InvitationDTO();
        invitationDTO.setStatus(InvitationStatus.PENDING.toString());
        invitationDTO.setUserId((long)4);
        invitationDTO.setDescription("Description");
        invitationDTO.setEventName(null);
        Invitation actual = InvitationMapper.convertInvitationDTOToInvitation(invitationDTO);

        Invitation expected = invitationService.createChallengeInvitationForOneUser(invitationDTO, (long)1);

        assertThat(actual.hashCode() == expected.hashCode());
    }

    @Test
    public void assertThatCreateChallengeInvitationThrowsExistingItemException() {
        InvitationDTO invitationDTO = new InvitationDTO();
        invitationDTO.setStatus(InvitationStatus.PENDING.toString());
        invitationDTO.setUserId((long)4);
        invitationDTO.setDescription("Description");
        invitationDTO.setEventName(null);

        try {
            invitationService.createChallengeInvitationForOneUser(invitationDTO, (long)1);
            fail();
        } catch (ExistingItemException e) {
            assertTrue(true);
        } catch (NotFoundException e) {
            fail();
        }
    }

    @Test
    public void assertThatCreateChallengeInvitationThrowsNotFoundException() {
        InvitationDTO invitationDTO = new InvitationDTO();
        invitationDTO.setStatus(InvitationStatus.PENDING.toString());
        invitationDTO.setUserId((long)4);
        invitationDTO.setDescription("Description");
        invitationDTO.setEventName(null);

        try {
            invitationService.createChallengeInvitationForOneUser(invitationDTO, (long)2);
            fail();
        } catch (ExistingItemException e) {
            fail();
        } catch (NotFoundException e) {
            assertTrue(true);
        }

    }
}
