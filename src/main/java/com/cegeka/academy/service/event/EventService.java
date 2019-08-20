package com.cegeka.academy.service.event;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.User;

import java.util.List;
import java.util.Optional;

public interface EventService {

    public List<Event> getAllPubicEvents();

    List<Event> getAllByUser(User owner);

    Optional<Event> getEvent(Long id);

    void createEvent(Event event);

    void updateEvent(Event event);

    void deleteEventById(Long id);

    List<Event> findAllEventsByOwnerID(Long id);
}
