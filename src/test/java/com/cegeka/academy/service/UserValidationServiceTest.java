package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Address;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.AddressRepository;
import com.cegeka.academy.repository.EventRepository;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.repository.UserRepository;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import com.cegeka.academy.service.invitation.InvitationService;
import com.cegeka.academy.service.serviceValidation.ValidationAccessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class UserValidationServiceTest {

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationAccessService validationAccessService;

    @Autowired
    private InvitationRepository invitationRepository;
  
    @Autowired
    private AddressRepository addressRepository;

    private User user;
    private Invitation invitation;

    @BeforeEach
    public void init() {

        user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        Address address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);
        Event event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
        eventRepository.save(event);
        invitation = TestsRepositoryUtil.createInvitation("pending", "ana are mere", event, user);
        invitationService.saveInvitation(invitation);
    }

    @Test
    @Transactional
    public void assertThatLoggedUserIsRecipientUser() {

        List<Invitation> list = invitationRepository.findAll();
        boolean isTheSameUser = validationAccessService.verifyUserAccessForInvitationEntity(list.get(0).getId());
        assertThat(isTheSameUser).isEqualTo(false);

    }

    @Test
    @Transactional
    public void assertThatLoggedUserIsRecipientUserWithInvalidInvitationId() {

        boolean isTheSameUser = validationAccessService.verifyUserAccessForInvitationEntity(100L);
        assertThat(isTheSameUser).isEqualTo(false);

    }

    @Test
    @Transactional
    public void assertThatLoggedUserIsRecipientUserWithNullInvitationId() {

        List<Invitation> list = invitationRepository.findAll();
        boolean isTheSameUser = validationAccessService.verifyUserAccessForInvitationEntity(null);
        assertThat(isTheSameUser).isEqualTo(false);

    }


}