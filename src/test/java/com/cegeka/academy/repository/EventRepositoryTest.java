package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.*;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import org.junit.jupiter.api.BeforeEach;
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

    private User user;
    private Event event;
    private Invitation invitation;
    private Address address;
    private Set<Category> categories;
    private Set<Category> categoriesHelper;
    private Category category1;

    @BeforeEach
    public void init() {
        user = TestsRepositoryUtil.createUser("login", "anaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.save(user);
        address = TestsRepositoryUtil.createAddress("Romania", "Bucuresti", "Splai", "333", "Casa", "Casa magica");
        addressRepository.saveAndFlush(address);
        category1 = TestsRepositoryUtil.createCategory("Sport", "Liber pentru toate varstele!");
        Category category3 = TestsRepositoryUtil.createCategory("Arta", "Expozitii de arta");
        Category category2 = TestsRepositoryUtil.createCategory("Social", "Actiuni caritabile");
        categoryRepository.save(category1);
        categoryRepository.save(category3);
        categoryRepository.save(category2);
        categories = new HashSet<>();
        categories.add(category1);
        categories.add(category3);
        categoriesHelper = new HashSet<>();
        categoriesHelper.add(category1);
        categoriesHelper.add(category2);


    }

    @Test
    public void testAddEvent() {
        event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user, categories, "https://scontent.fotp3-2.fna.fbcdn.net/v/t1.0-9/67786277_2592710307438854_5055220041180512256");
        eventRepository.save(event);
        Event eventTest = eventRepository.findAllByIsPublicIsTrue().get(0);
        assertThat(eventTest.isPublic()).isEqualTo(true);
        assertThat(eventTest.getName()).isEqualTo(event.getName());
    }

    @Test
    public void testFindAllByIsPublicIsTrue() {
        for (int i = 0; i < 5; i++) {
            Event event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user, categories, "https://scontent.fotp3-2.fna.fbcdn.net/v/t1.0-9/67786277_2592710307438854_5055220041180512256");
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

        Event event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user, categories, "https://scontent.fotp3-2.fna.fbcdn.net/v/t1.0-9/67786277_2592710307438854_5055220041180512256");
        eventRepository.save(event);
        user.getEvents().add(event);
        userRepository.save(user);

        Event event1 = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user, categories, "https://scontent.fotp3-2.fna.fbcdn.net/v/t1.0-9/67786277_2592710307438854_5055220041180512256");
        eventRepository.save(event1);
        user.getEvents().add(event1);
        userRepository.save(user);

        List<Event> events = eventRepository.findByUsers_id(user.getId());
        assertThat(events.size()).isEqualTo(2);
    }


    @Test
    public void testFindByCategory_id() {
        Event event1 = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user, categories, "https://scontent.fotp3-2.fna.fbcdn.net/v/t1.0-9/67786277_2592710307438854_5055220041180512256");
        eventRepository.save(event1);
        Event event2 = TestsRepositoryUtil.createEvent("Vanzare tablou", "Arta vie", false, address, user, categoriesHelper, "E:\\photos");
        eventRepository.save(event2);
        List<Event> eventsCategory_1 = eventRepository.findAllByCategories_id(category1.getId());
        assertThat(eventsCategory_1.size()).isEqualTo(2);
    }

    @Test
    public void testFindAllByEvents_id() {
        User user1 = TestsRepositoryUtil.createUser("login2", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa2");

        Event event = TestsRepositoryUtil.createEvent("Ana are mere!", "KFC Krushers Party", true, address, user, categories, "https://scontent.fotp3-2.fna.fbcdn.net/v/t1.0-9/67786277_2592710307438854_5055220041180512256");
        eventRepository.save(event);

        user.getEvents().add(event);
        userRepository.save(user);

        user1.getEvents().add(event);
        userRepository.save(user1);

        List<User> users = userRepository.findAllByEvents_id(event.getId());
        assertThat(users.size()).isEqualTo(2);

    }


}
