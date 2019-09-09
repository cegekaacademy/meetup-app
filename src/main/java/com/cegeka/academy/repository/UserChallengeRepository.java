package com.cegeka.academy.repository;

import com.cegeka.academy.domain.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {

    List<UserChallenge> findAllByInvitationId(Long challengeId);

    List<UserChallenge> findAllByUserId(Long userId);

    List<UserChallenge> findAllByUserIdAndChallengeIdAndInvitationId(Long userId, Long challengeId, Long invitationId);

    UserChallenge findAllByUserIdAndChallengeId(Long userId, Long challengeId);

    Optional<UserChallenge> findByUserIdAndChallengeIdAndInvitationId(Long userId, Long challengeId, Long invitationId);

    List<UserChallenge> findAllByChallengeId(Long challengeId);

    Optional<UserChallenge> findByUserIdAndChallengeId(Long userId, Long challengeId);

    List<UserChallenge> findAllByUserIdAndInvitationStatus(Long userId, String status);
}
