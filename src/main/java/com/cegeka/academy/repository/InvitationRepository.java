package com.cegeka.academy.repository;

import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.service.dto.InvitationDbDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

     List<Invitation> findByStatus(String status);
}
