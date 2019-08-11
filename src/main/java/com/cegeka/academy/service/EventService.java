package com.cegeka.academy.service;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.EventRepository;
import com.cegeka.academy.service.dto.EventDTO;
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

    public String updateOwnerEvent(EventDTO event){
         if(event.getEvent().getOwner().getId().equals(event.getOwnerEvent().getId()))
         {
             eventRepository.save(event.getEvent());
             return "Event updated";
         }
         else
         {
             return "This event can be modified only by the owner";
         }
    }

}
