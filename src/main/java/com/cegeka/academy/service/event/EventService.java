package com.cegeka.academy.service.event;


import com.cegeka.academy.domain.Event;

import java.util.List;

public interface EventService {

    List<Event> findAllEventsByOwnerID(Long id);
}
