package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Event;
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
    EventRepository eventRepository;

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
}
