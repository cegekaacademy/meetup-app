package com.cegeka.academy.web.rest;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.service.event.EventService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        Assert.notNull(eventService, "Service must not be null!");
        this.eventService = eventService;
    }

    @GetMapping()
    public List<Event> getEventsByOwnerId(@RequestParam Long ownerId) {
        return eventService.findAllEventsByOwnerID(ownerId);
    }
}
