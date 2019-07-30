package com.cegeka.academy.repository;

import com.cegeka.academy.domain.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {

    Optional<UserChallenge> findById(Long id);

    List<UserChallenge> findAllByChallengeId(Long challengeId);

    List<UserChallenge> findAllByUserId(Long userId);

}
