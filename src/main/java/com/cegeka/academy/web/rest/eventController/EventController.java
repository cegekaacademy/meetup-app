package com.cegeka.academy.web.rest.eventController;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.service.EventService;
import com.cegeka.academy.service.dto.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("event")
public class EventController {
    private final EventService eventService;
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PutMapping("/update")
    public String replaceEvent(@RequestBody EventDTO newEvent){
        return eventService.updateOwnerEvent(newEvent);
    }
}
