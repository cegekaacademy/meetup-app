package com.cegeka.academy.repository;

import com.cegeka.academy.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

     List<Invitation> findByStatus(String status);

     @Query(value = "SELECT u.id FROM jhi_user u WHERE u.id =  (select i.id_invitation_user from invitation i where i.id = :invitation)", nativeQuery = true)
     Stream<BigInteger> findInvitedUser(@Param("invitation") Optional<Invitation> invitation);

}
