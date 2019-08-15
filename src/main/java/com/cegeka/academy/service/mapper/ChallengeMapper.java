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


}
