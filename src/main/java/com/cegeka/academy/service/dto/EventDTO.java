package com.cegeka.academy.service.dto;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.User;

public class EventDTO {
    Event event;
    User ownerEvent;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getOwnerEvent() {
        return ownerEvent;
    }

}
