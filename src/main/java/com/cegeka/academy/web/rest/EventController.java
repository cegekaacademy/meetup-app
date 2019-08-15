package com.cegeka.academy.web.rest;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.service.event.EventService;
import com.cegeka.academy.service.invitation.ValidationAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event")
public class EventController {

    private final EventService eventService;
    private final ValidationAccessService validationAccessService;

    @Autowired
    public EventController(EventService eventService, ValidationAccessService validationAccessService) {
        this.eventService = eventService;
        this.validationAccessService = validationAccessService;
    }

    @GetMapping("/all_public")
    public List<Event> getAllPublicEvents() {
        return eventService.getAllPubicEvents();
    }

    @PostMapping
    public void createEvent(@RequestBody Event event) {
        eventService.createEvent(event);
    }

    @PutMapping
    public void updateEvent(@RequestBody Event event) {
        if (validationAccessService.verifyUserAccessForEventEntity(event.getId()))
            eventService.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        if (validationAccessService.verifyUserAccessForEventEntity(id))
            eventService.deleteEventById(id);
    }

}
