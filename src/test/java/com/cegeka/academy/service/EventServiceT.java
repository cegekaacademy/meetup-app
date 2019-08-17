package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Address;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.AddressRepository;
import com.cegeka.academy.repository.UserRepository;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import com.cegeka.academy.service.event.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class EventServiceT {

    private @Autowired
    UserRepository userRepository;
    private @Autowired
    AddressRepository addressRepository;
    private @Autowired
    EventService eventService;

    private Event event;

    @BeforeEach
    void init() {
        User user = TestsRepositoryUtil.createUser("cosminalex", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.saveAndFlush(user);
        Address address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.save(address);
        event = TestsRepositoryUtil.createEvent("Petrecere", "GAIA party", true, address, user);
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

}
