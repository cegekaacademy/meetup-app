package com.cegeka.academy.web.rest;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.repository.EventRepository;
import com.cegeka.academy.service.event.EventService;
import com.cegeka.academy.service.serviceValidation.ExpirationCheckService;
import com.cegeka.academy.service.serviceValidation.ValidationAccessService;
import com.cegeka.academy.web.rest.errors.ExpiredObjectException;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import com.cegeka.academy.web.rest.errors.UnauthorizedUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("event")
public class EventController {

    private final EventService eventService;
    private final ValidationAccessService validationAccessService;
    private final ExpirationCheckService expirationCheckService;
    private final EventRepository eventRepository;

    @Autowired
    public EventController(EventService eventService, ValidationAccessService validationAccessService,
                           ExpirationCheckService expirationCheckService, EventRepository eventRepository) {
        this.eventService = eventService;
        this.validationAccessService = validationAccessService;
        this.expirationCheckService = expirationCheckService;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/all_public")
    public List<Event> getAllPublicEvents() {
        return eventService.getAllPubicEvents();
    }

    @PostMapping
    public void createEvent(@Valid @RequestBody Event event) {

        eventService.createEvent(event);
    }

    @PutMapping
    public void updateEvent(@RequestBody Event event) throws NotFoundException {

        Optional<Event> eventUpdate = eventRepository.findById(event.getId());

        if (eventUpdate.isPresent()) {

            if (validationAccessService.verifyUserAccessForEventEntity(event.getId()) && expirationCheckService.availabilityCheck(event.getEndDate())) {

                eventService.updateEvent(event);

            } else if (!validationAccessService.verifyUserAccessForEventEntity(event.getId())) {

                throw new UnauthorizedUserException();

            } else if (!expirationCheckService.availabilityCheck(event.getEndDate())) {

                throw new ExpiredObjectException();

            }
        } else {

            throw new NotFoundException();
        }

    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) throws NotFoundException {

        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {

            if (validationAccessService.verifyUserAccessForEventEntity(id)) {

                eventService.deleteEventById(id);

            } else {

                throw new UnauthorizedUserException();

            }
        } else {

            throw new NotFoundException();
        }

    }

    @PutMapping("addUser/{id}")
    public void addUserToPublicEvent(@PathVariable(value = "id") Long id, @Valid @RequestBody Event event) throws NotFoundException {

        eventService.addUserToPublicEvent(id, event);

    }

}
