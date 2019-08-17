package com.cegeka.academy.web.rest;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.repository.EventRepository;
import com.cegeka.academy.service.event.EventService;
import com.cegeka.academy.service.serviceValidation.ExpirationCheckService;
import com.cegeka.academy.service.serviceValidation.ValidationAccessService;
import com.cegeka.academy.web.rest.errors.controllerException.ErrorResponse;
import com.cegeka.academy.web.rest.errors.controllerException.GeneralExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public void createEvent(@RequestBody Event event) {

        eventService.createEvent(event);
    }

    @PutMapping
    public ResponseEntity<ErrorResponse> updateEvent(@RequestBody Event event) {

        if (validationAccessService.verifyUserAccessForEventEntity(event.getId()) && expirationCheckService.availabilityCheck(event.getEndDate())) {

            eventService.updateEvent(event);

        } else if (!validationAccessService.verifyUserAccessForEventEntity(event.getId())) {

            return new GeneralExceptionHandler().handleUnauthorizedAccessEvent(event);

        } else if (!expirationCheckService.availabilityCheck(event.getEndDate())) {

            return new GeneralExceptionHandler().handleExpiredEvent(event);

        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorResponse> deleteEvent(@PathVariable Long id) {

        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {

            if (validationAccessService.verifyUserAccessForEventEntity(id)) {
                eventService.deleteEventById(id);

            } else {

                return new GeneralExceptionHandler().handleUnauthorizedAccessEvent(event.get());

            }
        } else {

            return new GeneralExceptionHandler().handleNotFoundEvent(id);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
