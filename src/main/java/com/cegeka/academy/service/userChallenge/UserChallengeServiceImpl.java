package com.cegeka.academy.service.userChallenge;

import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.domain.enums.ChallengeStatus;
import com.cegeka.academy.domain.enums.InvitationStatus;
import com.cegeka.academy.domain.enums.UserChallengeStatus;
import com.cegeka.academy.repository.ChallengeRepository;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.repository.UserChallengeRepository;
import com.cegeka.academy.repository.UserRepository;
import com.cegeka.academy.service.dto.ChallengeDTO;
import com.cegeka.academy.service.dto.UserChallengeDTO;
import com.cegeka.academy.service.mapper.ChallengeMapper;
import com.cegeka.academy.service.mapper.UserChallengeMapper;
import com.cegeka.academy.web.rest.errors.InvalidInvitationStatusException;
import com.cegeka.academy.web.rest.errors.InvalidUserChallengeStatusException;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import com.cegeka.academy.web.rest.errors.WrongOwnerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserChallengeServiceImpl implements UserChallengeService {

    @Autowired
    private UserChallengeRepository userChallengeRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserChallengeDTO> getUserChallengesByUserId(Long userId) {

        List<UserChallenge> userChallengeList = userChallengeRepository.findAllByUserId(userId);

        return userChallengeList.stream().map(userChallenge -> UserChallengeMapper.convertUserChallengeToUserChallengeDTO(userChallenge)).collect(Collectors.toList());

    }

    @Override
    public void updateUserChallengeStatus(Long userChallengeId, String status) throws NotFoundException, InvalidUserChallengeStatusException {

        UserChallenge userChallenge = userChallengeRepository.findById(userChallengeId)
                .orElseThrow(() -> new NotFoundException().setMessage("User challenge does not exists."));

        userChallenge.setStatus(UserChallengeStatus.getUserChallengeStatus(status).toString());

        userChallengeRepository.save(userChallenge);

    }

    @Override
    public void updateUserChallengeInvitationStatus(Long userChallengeId, String status) throws NotFoundException, InvalidInvitationStatusException {

        UserChallenge userChallenge = userChallengeRepository.findById(userChallengeId)
                .orElseThrow(() -> new NotFoundException().setMessage("User challenge does not exists."));

        userChallenge.getInvitation().setStatus(InvitationStatus.getInvitationStatus(status).toString());

        userChallengeRepository.save(userChallenge);

    }

    @Override
    public UserChallenge rateUser(UserChallengeDTO userChallengeDTO, Long ownerId)
            throws WrongOwnerException {

        long userId = userChallengeDTO.getUser().getId();
        long challengeId = userChallengeDTO.getChallenge().getId();
        long invitationId = userChallengeDTO.getInvitation().getId();
        UserChallenge userChallenge = userChallengeRepository
                .findByUserIdAndChallengeIdAndInvitationId(userId, challengeId, invitationId).orElseThrow(NoSuchElementException::new);

        userChallenge.setPoints(userChallengeDTO.getPoints());

        if (userChallenge.getChallenge().getCreator().getId().equals(ownerId)) {
            return userChallengeRepository.save(userChallenge);
        } else {
            throw new WrongOwnerException();
        }
    }

    @Override
    public UserChallenge initUserChallenge(Challenge challenge, Invitation invitation) throws NotFoundException {

        UserChallenge userChallenge = new UserChallenge();

        challengeRepository.findById(challenge.getId()).orElseThrow(
                () -> new NotFoundException().setMessage("Challenge not found"));
        invitationRepository.findById(invitation.getId()).orElseThrow(
                () -> new NotFoundException().setMessage("Invitation not found"));
        User invitedUser = invitation.getUser();
        userRepository.findById(invitedUser.getId()).orElseThrow(
                () -> new NotFoundException().setMessage("User not found"));

        userChallenge.setChallenge(challenge);
        userChallenge.setPoints(0);
        userChallenge.setUser(invitation.getUser());
        userChallenge.setInvitation(invitation);
        userChallenge.setStatus("Not started");
        userChallenge.setChallengeAnswer(null);
        userChallenge.setStartTime(new Date());
        userChallenge.setEndTime(new Date());

        return userChallengeRepository.save(userChallenge);
    }

    @Override
    public List<ChallengeDTO> getNextChallengesForAnUser(Long userId) throws NotFoundException {

        List<UserChallenge> userChallengeList = userChallengeRepository.findAllByUserId(userId);

        if(userChallengeList ==  null || userChallengeList.isEmpty()){

            throw new NotFoundException().setMessage("List is empty");

        }

        List<UserChallenge> userChallengeListWithValidInvitation = userChallengeList.stream().filter(userChallenge -> hasUserChallengeValidInvitation(userChallenge)).collect(Collectors.toList());

        if(userChallengeListWithValidInvitation ==  null || userChallengeListWithValidInvitation.isEmpty()){

            throw new NotFoundException().setMessage("List is empty");

        }

        List<Challenge> challengeList = userChallengeListWithValidInvitation.stream().map(userChallenge -> userChallenge.getChallenge()).collect(Collectors.toList());

        List<Challenge> validChallengeList = challengeList.stream().filter(challenge -> isAfterToday(challenge.getStartDate())).collect(Collectors.toList());

        if(validChallengeList == null || validChallengeList.isEmpty()){

            throw new NotFoundException().setMessage("List is empty");

        }

        return validChallengeList.stream().map(challenge -> ChallengeMapper.convertChallengeToChallengeDTO(challenge)).collect(Collectors.toList());

    }

    private boolean isAfterToday(Date date){

        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .isAfter(LocalDate.now());
    }

    private boolean hasUserChallengeValidInvitation(UserChallenge userChallenge){

        return (userChallenge.getChallenge().getStatus().equalsIgnoreCase(ChallengeStatus.PUBLIC.toString())) ||
                (userChallenge.getChallenge().getStatus().equalsIgnoreCase(ChallengeStatus.PRIVATE.toString()) &&
                userChallenge.getInvitation() != null &&
                !userChallenge.getInvitation().getStatus().equalsIgnoreCase(InvitationStatus.CANCELED.toString()) &&
                !userChallenge.getInvitation().getStatus().equalsIgnoreCase(InvitationStatus.REJECTED.toString()));

    }
}
