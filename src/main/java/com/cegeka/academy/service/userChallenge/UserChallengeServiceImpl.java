package com.cegeka.academy.service.userChallenge;

import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.repository.UserChallengeRepository;
import com.cegeka.academy.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserChallengeServiceImpl implements UserChallengeService {

    @Autowired
    private UserChallengeRepository userChallengeRepository;

    @Override
    public List<UserChallengeDTO> getUserChallengesByUserId(Long userId) {

        List<UserChallenge> userChallengeList = userChallengeRepository.findAllByUserId(userId);

        return userChallengeList.stream().map(userChallenge -> convertUserChallengeToUserChallengeDTO(userChallenge)).collect(Collectors.toList());

    }

    public static UserChallenge convertUserChallengeDTOToUserChallenge(UserChallengeDTO userChallengeDTO){

        if(userChallengeDTO != null){

            Invitation invitation = new Invitation();
            invitation.setInvitedUser(convertUserForUserChallengeDTOToUser(userChallengeDTO.getInvitation().getUser()));
            invitation.setId(userChallengeDTO.getInvitation().getId());
            invitation.setStatus(userChallengeDTO.getInvitation().getStatus());

            UserChallenge userChallenge = new UserChallenge();
            userChallenge.setId(userChallengeDTO.getId());
            userChallenge.setUser(convertUserForUserChallengeDTOToUser(userChallengeDTO.getUser()));
            userChallenge.setInvitation(invitation);
            userChallenge.setChallenge(convertChallengeForUserChallengeDTOToChallenge(userChallengeDTO.getChallenge()));
            userChallenge.setStatus(userChallengeDTO.getStatus());
            userChallenge.setPoints(userChallengeDTO.getPoints());
            userChallenge.setStartTime(userChallengeDTO.getStartTime());
            userChallenge.setEndTime(userChallengeDTO.getEndTime());

            return userChallenge;
        }

        return null;
    }

    public UserChallengeDTO convertUserChallengeToUserChallengeDTO(UserChallenge userChallenge){

        if(userChallenge != null){

            InvitationForUserChallengeDTO invitationForUserChallengeDTO = new InvitationForUserChallengeDTO();
            invitationForUserChallengeDTO.setUser(convertUserToUserForUserChallengeDTO(userChallenge.getInvitation().getInvitedUser()));
            invitationForUserChallengeDTO.setId(userChallenge.getInvitation().getId());
            invitationForUserChallengeDTO.setStatus(userChallenge.getInvitation().getStatus());

            UserChallengeDTO userChallengeDTO = new UserChallengeDTO();
            userChallengeDTO.setId(userChallenge.getId());
            userChallengeDTO.setUser(convertUserToUserForUserChallengeDTO(userChallenge.getUser()));
            userChallengeDTO.setInvitation(invitationForUserChallengeDTO);
            userChallengeDTO.setChallenge(convertChallengeToChallengeForUserChallengeDTO(userChallenge.getChallenge()));
            userChallengeDTO.setStatus(userChallenge.getStatus());
            userChallengeDTO.setPoints(userChallenge.getPoints());
            userChallengeDTO.setStartTime(userChallenge.getStartTime());
            userChallengeDTO.setEndTime(userChallenge.getEndTime());

            return userChallengeDTO;
        }

        return null;
    }

    public UserForUserChallengeDTO convertUserToUserForUserChallengeDTO(User user){

        UserForUserChallengeDTO userForUserChallengeDTO = new UserForUserChallengeDTO();
        userForUserChallengeDTO.setId(user.getId());
        userForUserChallengeDTO.setFirstName(user.getFirstName());
        userForUserChallengeDTO.setLastName(user.getLastName());
        userForUserChallengeDTO.setEmail(user.getEmail());

        return userForUserChallengeDTO;
    }

    public static User convertUserForUserChallengeDTOToUser(UserForUserChallengeDTO userForUserChallengeDTO){

        User user = new User();
        user.setId(userForUserChallengeDTO.getId());
        user.setFirstName(userForUserChallengeDTO.getFirstName());
        user.setLastName(userForUserChallengeDTO.getLastName());
        user.setEmail(userForUserChallengeDTO.getEmail());

        return user;
    }

    public ChallengeForUserChallengeDTO convertChallengeToChallengeForUserChallengeDTO(Challenge challenge){

        ChallengeForUserChallengeDTO challengeForUserChallengeDTO = new ChallengeForUserChallengeDTO();
        challengeForUserChallengeDTO.setId(challenge.getId());
        challengeForUserChallengeDTO.setUser(convertUserToUserForUserChallengeDTO(challenge.getCreator()));
        challengeForUserChallengeDTO.setStartDate(challenge.getStartDate());
        challengeForUserChallengeDTO.setEndDate(challenge.getEndDate());
        challengeForUserChallengeDTO.setPoints(challenge.getPoints());

        return challengeForUserChallengeDTO;
    }

    public static Challenge convertChallengeForUserChallengeDTOToChallenge(ChallengeForUserChallengeDTO challengeForUserChallengeDTO){

        Challenge challenge = new Challenge();
        challenge.setId(challenge.getId());
        challenge.setCreator(convertUserForUserChallengeDTOToUser(challengeForUserChallengeDTO.getUser()));
        challenge.setStartDate(challengeForUserChallengeDTO.getStartDate());
        challenge.setEndDate(challengeForUserChallengeDTO.getEndDate());
        challenge.setPoints(challengeForUserChallengeDTO.getPoints());

        return challenge;
    }


}
