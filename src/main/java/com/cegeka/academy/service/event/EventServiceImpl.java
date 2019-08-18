package com.cegeka.academy.service.event;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.EventRepository;
import com.cegeka.academy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserService userService;

    private Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }


    public List<Event> getAllPubicEvents() {
        return eventRepository.findAllByIsPublicIsTrue();
    }

    public List<Event> getAllByUser(User owner) {
        return eventRepository.findAllByOwner(owner);
    }

    @Override
    public Optional<Event> getEvent(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public void createEvent(Event event) {
        if (userService.getUserWithAuthorities().isPresent()) {
            User currentUser = userService.getUserWithAuthorities().get();
            event.setOwner(currentUser);
            logger.info("Event with id: " + eventRepository.save(event).getId() + "  was saved to database.");
        }
        //TODO:replace that
        else {
            logger.info("Event with id: " + eventRepository.save(event).getId() + "  was saved to database.");
        }

    }

    @Override
    public void updateEvent(Event event) {
        logger.info("Event with id: " + eventRepository.save(event).getId() + "  was updated into database.");
    }

    @Override
    public void deleteEventById(Long id) {
        eventRepository.findById(id).ifPresent(eventRepository::delete);
    }

}
