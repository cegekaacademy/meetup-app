package com.cegeka.academy.repository;

import com.cegeka.academy.domain.Group;
import com.cegeka.academy.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link Group} entity.
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
//    Group findGroupById(Long id);
}
