package com.cegeka.academy.service.event;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.service.dto.EventDTO;
import com.cegeka.academy.web.rest.errors.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface EventService {

    public List<Event> getAllPubicEvents();

    List<Event> getAllByOwner(User owner);

    Optional<Event> getEvent(Long id);

    void createEvent(Event event);

    void updateEvent(Event event);

    void deleteEventById(Long id);

    void addUserToPublicEvent(Long userId, Event event) throws NotFoundException;

    List<EventDTO> getEventsByUser(Long userId) throws NotFoundException;
}
