package com.cegeka.academy.repository;

import com.cegeka.academy.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
     List<Invitation> findByStatus(String status);
}
