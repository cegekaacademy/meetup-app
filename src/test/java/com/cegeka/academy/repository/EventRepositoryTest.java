package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Address;
import com.cegeka.academy.domain.Event;
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
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;


    @Test
    public void testAddEvent() {
        User user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        Address address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);
        Event event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
        eventRepository.save(event);
        Event eventTest = eventRepository.findAllByIsPublicIsTrue().get(0);
        assertThat(eventTest.getPublic()).isEqualTo(true);
        assertThat(eventTest.getName()).isEqualTo(event.getName());
    }

    @Test
    public void testFindAllByIsPublicIsTrue() {

        for (int i = 0; i < 5; i++) {
            User user = TestsRepositoryUtil.createUser("abcd" + i, "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
            userRepository.save(user);
            Address address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
            addressRepository.saveAndFlush(address);
            Event event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
            if (i % 2 == 0) {
                event.setPublic(true);
            } else {
                event.setPublic(false);
            }
            eventRepository.save(event);
        }

        List<Event> publicEvents = eventRepository.findAllByIsPublicIsTrue();
        assertThat(publicEvents.size()).isEqualTo(3);
    }

    @Test
    public void testFindByUsers_id() {

        User user = TestsRepositoryUtil.createUser("login", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Address address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);
        Event event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
        eventRepository.save(event);
        user.getEvents().add(event);
        userRepository.save(user);

        Event event1 = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
        eventRepository.save(event1);
        user.getEvents().add(event1);
        userRepository.save(user);

        List<Event> events = eventRepository.findByUsers_id(user.getId());
        assertThat(events.size()).isEqualTo(2);


    }

    @Test
    public void testFindAllByEvents_id() {

        User user = TestsRepositoryUtil.createUser("login", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        User user1 = TestsRepositoryUtil.createUser("login2", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa2");


        Address address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);

        Event event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
        eventRepository.save(event);

        user.getEvents().add(event);
        userRepository.save(user);

        user1.getEvents().add(event);
        userRepository.save(user1);

        List<User> users = userRepository.findAllByEvents_id(event.getId());
        assertThat(users.size()).isEqualTo(2);

    }


}
