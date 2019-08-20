package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.EventRepository;
import com.cegeka.academy.service.event.EventService;
import com.cegeka.academy.service.event.EventServiceImpl;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class EventServiceTest {

    private final EventRepository eventRepository;
    private final EventServiceImpl eventService;

    @Autowired
    public EventServiceTest(EventRepository eventRepository, EventServiceImpl eventService) {
        Assert.notNull(eventRepository, "Repository must not be null!");
        this.eventRepository = eventRepository;
        Assert.notNull(eventService, "Repository must not be null!");
        this.eventService = eventService;
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

        List<Event> events = eventService.findAllEventsByOwnerID(1L);
        assertThat(events.size()).isEqualTo(3);
    }

}
