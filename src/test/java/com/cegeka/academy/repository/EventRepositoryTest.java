package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.*;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private CategoryRepository categoryRepository;



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
    public void testFindAllByCategoryId() {
        User user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        Address address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);
        Event event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
        eventRepository.save(event);
        Category category1=TestsRepositoryUtil.createCategory("Ana","description");
        categoryRepository.save(category1);
        event.getCategories().add(category1);
        eventRepository.save(event);
        Category category2=TestsRepositoryUtil.createCategory("Dana","description");
        categoryRepository.save(category2);
        event.getCategories().add(category2);
        eventRepository.save(event);
        List<Event>events=eventRepository.findAllByCategories_id(category1.getId());
        assertThat(events.size()).isEqualTo(1);
    }
    @Test
    public void testFindAllByEventId()
    {
        User user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        Address address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);
        Event event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user);
        eventRepository.save(event);
        Category category1=TestsRepositoryUtil.createCategory("Ana","description");
        categoryRepository.save(category1);
        event.getCategories().add(category1);
        eventRepository.save(event);
        Category category2=TestsRepositoryUtil.createCategory("Dana","description");
        categoryRepository.save(category2);
        event.getCategories().add(category2);
        eventRepository.save(event);
        Category category3=TestsRepositoryUtil.createCategory("Doriana","description");
        categoryRepository.save(category3);
        event.getCategories().add(category3);
        eventRepository.save(event);
        List<Category>categories=categoryRepository.findAllByEvents_id(event.getId());
        assertThat(categories.size()).isEqualTo(3);

    }

    @Test
    public void testFindAllByIsPublicIsTrue() {

        for (int i = 0; i < 5; i++) {
            User user = TestsRepositoryUtil.createUser("login" + i, "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
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
