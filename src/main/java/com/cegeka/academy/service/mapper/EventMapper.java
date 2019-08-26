package com.cegeka.academy.service.mapper;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.service.dto.EventDTO;

public class EventMapper {

    public static Event convertEventDTOtoEvent(EventDTO eventDTO) {
        Event event = new Event();
        event.setPublic(eventDTO.getPublic());
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setNotes(eventDTO.getNotes());
        event.setStartDate(eventDTO.getStartDate());
        event.setEndDate(eventDTO.getEndDate());
        return event;
    }
}
