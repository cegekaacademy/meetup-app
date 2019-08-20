package com.cegeka.academy.service.event;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.repository.EventRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        Assert.notNull(eventRepository, "Repository must not be null!");
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> findAllEventsByOwnerID(Long id) {
        return eventRepository.findAllByOwner_Id(id);
    }
}
