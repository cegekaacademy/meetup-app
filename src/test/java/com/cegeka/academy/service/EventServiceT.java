package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Address;
import com.cegeka.academy.domain.Category;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.AddressRepository;
import com.cegeka.academy.repository.CategoryRepository;
import com.cegeka.academy.repository.EventRepository;
import com.cegeka.academy.repository.UserRepository;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import com.cegeka.academy.service.dto.EventDTO;
import com.cegeka.academy.service.event.EventService;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class EventServiceT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Mock
    private UserService userService;


    private Event event;
    private User user;
    private User currentUser;
    private Category category1, category3;
    private Address address;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        user = TestsRepositoryUtil.createUser("cosminalex", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.saveAndFlush(user);
        address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.save(address);
        category1 = TestsRepositoryUtil.createCategory("Sport", "Liber pentru toate varstele!");
        category3 = TestsRepositoryUtil.createCategory("Arta", "Expozitii de arta");
        categoryRepository.save(category1);
        categoryRepository.save(category3);
        Set<Category> list1 = new HashSet<>();
        list1.add(category1);
        list1.add(category3);
        event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user, list1, "https://scontent.fotp3-2.fna.fbcdn.net/v/t1.0-9/67786277_2592710307438854_5055220041180512256");
        eventService.createEvent(event);
    }


    @Test
    @Transactional
    public void assertThatCreateEventWorks() {
        List<Event> events = eventService.getAllPubicEvents();
        assertThat(events.size()).isEqualTo(1);
        assertThat(events.get(0).getName()).isEqualTo(event.getName());
        assertThat(events.get(0).getOwner().getLogin()).isEqualTo(event.getOwner().getLogin());
    }

    @Test
    @Transactional
    public void assertThatUpdateEventWorks() {
        List<Event> events = eventService.getAllPubicEvents();
        event.setName("Macarena");
        event.setId(events.get(0).getId());
        eventService.updateEvent(event);
        assertThat(events.size()).isEqualTo(1);
        assertThat(events.get(0).getNotes()).isEqualTo(event.getNotes());
        assertThat(events.get(0).getName()).isEqualTo(event.getName());
        assertThat(events.get(0).getOwner()).isEqualTo(event.getOwner());
    }

    @Test
    @Transactional
    public void assertThatDeleteEventWorks() {
        List<Event> listBeforeDelete = eventService.getAllPubicEvents();
        eventService.deleteEventById(listBeforeDelete.get(0).getId());
        List<Event> listAfterDelete = eventService.getAllPubicEvents();
        assertThat(listAfterDelete.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void assertThatAddUserToPublicEvent_ThrowsExceptionWithWrongUserId() {
        Assertions.assertThrows(NotFoundException.class, () -> eventService.addUserToPublicEvent(event.getId(), 1001l));
    }

    @Test
    @Transactional
    public void assertThatAddUserToPublicEvent_ThrowsExceptionWithWrongEventId() {
        Assertions.assertThrows(NotFoundException.class, () -> eventService.addUserToPublicEvent(102l, user.getId()));
    }

    @Test
    @Transactional
    public void assertThatAddUserToPublicEventWorksWithPrivateEvent() throws NotFoundException {
        event.setPublicEvent(false);
        eventRepository.save(event);
        eventService.addUserToPublicEvent(event.getId(), user.getId());
        List<Event> events = eventRepository.findByUsers_id(user.getId());
        assertThat(events.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void assertThatAddUserToPublicEventWorksWithUser() throws NotFoundException {
        eventService.addUserToPublicEvent(event.getId(), user.getId());
        List<User> users = userRepository.findAllByEvents_id(event.getId());
        assertThat(users.size()).isEqualTo(1);

    }

    @Test
    @Transactional
    public void assertThatAddUserToPublicEventWorksWithEvent() throws NotFoundException {
        eventService.addUserToPublicEvent(event.getId(), user.getId());
        List<Event> events = eventRepository.findByUsers_id(user.getId());
        assertThat(events.size()).isEqualTo(1);

    }


    @Test
    @Transactional
    public void getEventsByUser_ThrowsException() {
        Assertions.assertThrows(NotFoundException.class, () -> eventService.getEventsByUser(1002l));
    }

    @Test
    @Transactional
    public void getEventsByUserWorks() throws NotFoundException {
        eventService.addUserToPublicEvent(event.getId(), user.getId());
        List<EventDTO> events = eventService.getEventsByUser(user.getId());
        assertThat(events.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void getAllByOwner_ThrowsException() {
        User notOwner = TestsRepositoryUtil.createUser("loginx", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.saveAndFlush(notOwner);
        Assertions.assertThrows(NotFoundException.class, () -> eventService.getAllByOwner(notOwner));
    }

    @Test
    @Transactional
    public void getAllByOwnerWorks() throws NotFoundException {
        List<EventDTO> events = eventService.getAllByOwner(user);
        assertThat(events.size()).isEqualTo(1);
    }
//
//    @Test
//    public void assertGetEventsByUserInterestedCategoriesIsWorkingWithInvalidArgument() {
//        Assertions.assertThrows(NotFoundException.class, () -> eventService.getEventsByUserInterestedCategories(100L));
//    }
//
//    @Test
//    public void assertGetEventsByUserInterestedCategoriesIsWorkingWithNoEvents() {
//        User user2 = TestsRepositoryUtil.createUser("aaaaaa", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
//        userRepository.saveAndFlush(user2);
//        Assertions.assertThrows(NotFoundException.class, () -> eventService.getEventsByUserInterestedCategories(user2.getId()));
//    }

    @WithMockUser
    @Test
    public void assertGetEventsByUserInterestedCategoriesIsWorkingWithValidData() throws NotFoundException {

        currentUser = userRepository.findAll().get(2);
        when(userService.getUserWithAuthorities()).thenReturn(Optional.of(currentUser));
        Set<Category> list2 = new HashSet<>();
        list2.add(category1);
        list2.add(category3);
        Event event2 = TestsRepositoryUtil.createEvent("Ana are mere!", "name", true, address, currentUser, list2, null);
        eventRepository.save(event2);
        currentUser.getEvents().add(event2);
        userRepository.save(user);
        List<EventDTO> eventDTOS = eventService.getEventsByUserInterestedCategories(currentUser.getId());
        assertThat(eventDTOS.size()).isEqualTo(2);


    }

    @Test
    @WithMockUser
    public void assertGetEventsByUserInterestedCategoriesIsWorkingWithNoResult() throws NotFoundException {
        currentUser = userRepository.findAll().get(2);
        when(userService.getUserWithAuthorities()).thenReturn(Optional.of(currentUser));
        eventRepository.save(event);
        user.getEvents().add(event);
        userRepository.save(user);
        Assertions.assertThrows(NotFoundException.class, () -> eventService.getEventsByUserInterestedCategories(currentUser.getId()));

    }

    @Test
    @WithMockUser
    public void assertGetEventsByUserInterestedCategoriesIsWorkingWithWrongCategory() throws NotFoundException {
        currentUser = userRepository.findAll().get(2);
        when(userService.getUserWithAuthorities()).thenReturn(Optional.of(currentUser));
        Set<Category> list2 = new HashSet<>();
        Category categoryNotInterested = TestsRepositoryUtil.createCategory("notinterested", "not interested");
        list2.add(categoryNotInterested);
        Event event2 = TestsRepositoryUtil.createEvent("Ana are mere!", "name", true, address, currentUser, list2, null);
        eventRepository.save(event2);
        user.getEvents().add(event);
        userRepository.save(user);
        List<EventDTO> eventDTOS = eventService.getEventsByUserInterestedCategories(user.getId());
        assertThat(eventDTOS.size()).isEqualTo(1);

    }

    @Test
    @WithMockUser
    public void assertFilterEventsByNameWorks() throws NotFoundException {
        currentUser = userRepository.findAll().get(2);
        Set<Category> list1 = new HashSet<>();
        list1.add(category1);
        list1.add(category3);
        when(userService.getUserWithAuthorities()).thenReturn(Optional.of(currentUser));
        eventService.createEvent(event);
        Event eventTest = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party Beach", false, address, currentUser, list1, "https://scontent.fotp3-2.fna.fbcdn.net/v/t1.0-9/67786277_2592710307438854_5055220041180512256");
        Event eventTest2 = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Party Beach", false, address, currentUser, list1, "https://scontent.fotp3-2.fna.fbcdn.net/v/t1.0-9/67786277_2592710307438854_5055220041180512256");
        eventService.createEvent(eventTest);
        eventService.createEvent(eventTest2);

        eventTest.setPublic(false);
        eventService.updateEvent(eventTest);

        List<EventDTO> eventDTOList = eventService.getEventsByName("Krushers");
        assertThat(eventDTOList.size()).isEqualTo(1);
    }
}
