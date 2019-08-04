package com.cegeka.academy.repository;

import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByOwner(User owner);

    List<Event> findAllByIsPublicIsTrue();
}
