package com.cegeka.academy.service.mapper;

import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.ChallengeCategory;
import com.cegeka.academy.service.dto.ChallengeCategoryDTO;
import com.cegeka.academy.service.dto.ChallengeDTO;


public class ChallengeMapper {


    public static Challenge convertChallengeDTOToChallenge(ChallengeDTO challengeDTO){

        Challenge challenge = new Challenge();
        challenge.setEndDate(challengeDTO.getEndDate());
        challenge.setStartDate(challengeDTO.getStartDate());
        challenge.setDescription(challengeDTO.getDescription());
        challenge.setPoints(challengeDTO.getPoints());
        challenge.setStatus(challengeDTO.getStatus());
        challenge.setEndDate(challengeDTO.getEndDate());

        if (challengeDTO.getCreator() != null) {

            challenge.setCreator(new UserMapper().userDTOToUser(challengeDTO.getCreator()));
        }

        if(challengeDTO.getChallengeCategory() != null) {

            challenge.setChallengeCategory(convertChallengeCategoryDTOToChallengeCategory(challengeDTO.getChallengeCategory()));
        }

        return challenge;
    }

    public static ChallengeCategory convertChallengeCategoryDTOToChallengeCategory(ChallengeCategoryDTO challengeCategoryDTO){

        if(challengeCategoryDTO == null){

            return null;

        } else {

            ChallengeCategory challengeCategory = new ChallengeCategory();
            challengeCategory.setId(challengeCategoryDTO.getId());
            challengeCategory.setName(challengeCategoryDTO.getName());
            challengeCategory.setDescription(challengeCategoryDTO.getDescription());

            return challengeCategory;
        }

    }

    public static ChallengeCategoryDTO convertChallengeCategoryToChallengeCategoryDTO(ChallengeCategory challengeCategory){

        if(challengeCategory == null){

            return null;

        } else {

            ChallengeCategoryDTO challengeCategoryDTO = new ChallengeCategoryDTO();
            challengeCategoryDTO.setId(challengeCategory.getId());
            challengeCategoryDTO.setName(challengeCategory.getName());
            challengeCategoryDTO.setDescription(challengeCategory.getDescription());

            return challengeCategoryDTO;
        }

    }


    public static ChallengeDTO convertChallengeToChallengeDTO(Challenge challenge) {
        ChallengeDTO challengeDTO = new ChallengeDTO();
        challengeDTO.setEndDate(challenge.getEndDate());
        challengeDTO.setStartDate(challenge.getStartDate());
        challengeDTO.setDescription(challenge.getDescription());
        challengeDTO.setPoints(challenge.getPoints());
        challengeDTO.setStatus(challenge.getStatus());
        challengeDTO.setEndDate(challenge.getEndDate());

        if (challenge.getCreator() != null) {

            challengeDTO.setCreator(new UserMapper().userToUserDTO(challenge.getCreator()));
        }

        if(challenge.getChallengeCategory() != null) {

            challengeDTO.setChallengeCategory(
                    convertChallengeCategoryToChallengeCategoryDTO(
                            challenge.getChallengeCategory()));
        }

        return challengeDTO;
    }
}
