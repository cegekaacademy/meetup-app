package com.cegeka.academy.web.rest;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.service.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping()
    public List<Event> getEventsByOwnerId(@RequestParam Long ownerId) {
        return eventService.findAllEventsByOwnerID(ownerId);
    }
}
