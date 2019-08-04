package com.cegeka.academy.service;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventService {
    @Autowired
    EventRepository eventRepository;

    public List<Event> getAllPubicEvents() {
        return eventRepository.findAllByIsPublicIsTrue();
    }

    public List<Event> getAllByUser(User owner) {
        return eventRepository.findAllByOwner(owner);
    }

}
