package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.User;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.security.acl.Owner;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class EventRepositoryTest {

    private final EventRepository eventRepository;

    @Autowired
    public EventRepositoryTest(EventRepository eventRepository) {
        Assert.notNull(eventRepository, "Repository must not be null!");
        this.eventRepository = eventRepository;
    }

    @Test
    public void testAddEvent() {
        Event event = new Event();
        event.setDescription("Ana are mere!");
        event.setId(1234L);
        event.setName("KFC Krushers Party");
        event.setPublic(true);
        eventRepository.save(event);
        Event eventTest = eventRepository.findAllByIsPublicIsTrue().get(0);
        assertThat(eventTest.getPublic()).isEqualTo(true);
        assertThat(eventTest.getName()).isEqualTo(event.getName());
    }

    @Test
    public void testFindAllByIsPublicIsTrue() {

        for (int i = 0; i < 5; i++) {
            Event event = new Event();
            event.setDescription("Ana are mere!");
            event.setId(1234L);
            event.setName("KFC Krushers Party");
            if (i % 2 == 0)
                event.setPublic(true);
            else
                event.setPublic(false);
            eventRepository.save(event);
        }

        List<Event> publicEvents = eventRepository.findAllByIsPublicIsTrue();
        assertThat(publicEvents.size()).isEqualTo(3);
    }

    @Test
    public void testFindAllByOwnerId(){

        for (int i = 0; i < 3; i++) {
            User owner = new User();
            owner.setId(1L);
            Event event = new Event();
            event.setDescription("Ana are mere!");
            event.setId(1234L);
            event.setOwner(owner);
            event.setName("KFC Krushers Party");
            if (i % 2 == 0)
                event.setPublic(true);
            else
                event.setPublic(false);
            eventRepository.save(event);
        }
        User user = new User();
        user.setId(2L);
        Event event = new Event();
        event.setOwner(user);
        eventRepository.save(event);

        List<Event> events = eventRepository.findAllByOwner_Id(1L);
        assertThat(events.size()).isEqualTo(3);
    }
}
