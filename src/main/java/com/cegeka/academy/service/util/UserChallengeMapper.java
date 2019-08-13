package com.cegeka.academy.service.util;

import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.service.dto.ChallengeForUserChallengeDTO;
import com.cegeka.academy.service.dto.InvitationForUserChallengeDTO;
import com.cegeka.academy.service.dto.UserChallengeDTO;
import com.cegeka.academy.service.dto.UserForUserChallengeDTO;

public class UserChallengeMapper {

    public static UserChallengeDTO convertUserChallengeToUserChallengeDTO(UserChallenge userChallenge){

        if(userChallenge != null){

            UserChallengeDTO userChallengeDTO = new UserChallengeDTO();
            userChallengeDTO.setId(userChallenge.getId());
            userChallengeDTO.setUser(convertUserToUserForUserChallengeDTO(userChallenge.getUser()));
            userChallengeDTO.setInvitation(convertInvitationToInvitationForUserChallengeDTO(userChallenge.getInvitation()));
            userChallengeDTO.setChallenge(convertChallengeToChallengeForUserChallengeDTO(userChallenge.getChallenge()));
            userChallengeDTO.setStatus(userChallenge.getStatus());
            userChallengeDTO.setPoints(userChallenge.getPoints());
            userChallengeDTO.setStartTime(userChallenge.getStartTime());
            userChallengeDTO.setEndTime(userChallenge.getEndTime());

            return userChallengeDTO;
        }

        return null;
    }

    public static UserChallenge convertUserChallengeDTOToUserChallenge(UserChallengeDTO userChallengeDTO){

        if(userChallengeDTO != null){

            UserChallenge userChallenge = new UserChallenge();
            userChallenge.setId(userChallengeDTO.getId());
            userChallenge.setUser(convertUserForUserChallengeDTOToUser(userChallengeDTO.getUser()));
            userChallenge.setInvitation(convertInvitationForUserChallengeDTOTOInvitation(userChallengeDTO.getInvitation()));
            userChallenge.setChallenge(convertChallengeForUserChallengeDTOToChallenge(userChallengeDTO.getChallenge()));
            userChallenge.setStatus(userChallengeDTO.getStatus());
            userChallenge.setPoints(userChallengeDTO.getPoints());
            userChallenge.setStartTime(userChallengeDTO.getStartTime());
            userChallenge.setEndTime(userChallengeDTO.getEndTime());

            return userChallenge;
        }

        return null;
    }

    public static UserForUserChallengeDTO convertUserToUserForUserChallengeDTO(User user){

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

    public static ChallengeForUserChallengeDTO convertChallengeToChallengeForUserChallengeDTO(Challenge challenge){

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

    public static InvitationForUserChallengeDTO convertInvitationToInvitationForUserChallengeDTO(Invitation invitation){

        InvitationForUserChallengeDTO invitationForUserChallengeDTO = new InvitationForUserChallengeDTO();
        invitationForUserChallengeDTO.setUser(convertUserToUserForUserChallengeDTO(invitation.getInvitedUser()));
        invitationForUserChallengeDTO.setId(invitation.getId());
        invitationForUserChallengeDTO.setStatus(invitation.getStatus());

        return  invitationForUserChallengeDTO;
    }

    public static Invitation convertInvitationForUserChallengeDTOTOInvitation(InvitationForUserChallengeDTO invitationForUserChallengeDTO){

        Invitation invitation = new Invitation();
        invitation.setInvitedUser(convertUserForUserChallengeDTOToUser(invitationForUserChallengeDTO.getUser()));
        invitation.setId(invitationForUserChallengeDTO.getId());
        invitation.setStatus(invitationForUserChallengeDTO.getStatus());

        return invitation;
    }

}
