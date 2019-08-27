package com.cegeka.academy.service.mapper;

import com.cegeka.academy.domain.ChallengeAnswer;
import com.cegeka.academy.service.dto.ChallengeAnswerDTO;

public class ChallengeAnswerMapper {

    public static ChallengeAnswer convertChallengeAnswerDTOToChallengeAnswer(ChallengeAnswerDTO challengeAnswerDTO){

        if(challengeAnswerDTO == null){

            return null;

        } else {

            ChallengeAnswer challengeAnswer = new ChallengeAnswer();
            challengeAnswer.setId(challengeAnswerDTO.getId());
            challengeAnswer.setVideoAt(challengeAnswerDTO.getVideoAt());
            challengeAnswer.setImagePath(challengeAnswerDTO.getImagePath());
            challengeAnswer.setAnswer(challengeAnswerDTO.getAnswer());

            return challengeAnswer;
        }
    }

    public static ChallengeAnswerDTO convertChallengeAnswerToChallengeAnswerDTO(ChallengeAnswer challengeAnswer){

        if(challengeAnswer == null){

            return null;

        } else {

            ChallengeAnswerDTO challengeAnswerDTO = new ChallengeAnswerDTO();
            challengeAnswerDTO.setId(challengeAnswer.getId());
            challengeAnswerDTO.setVideoAt(challengeAnswer.getVideoAt());
            challengeAnswerDTO.setImagePath(challengeAnswer.getImagePath());
            challengeAnswerDTO.setAnswer(challengeAnswer.getAnswer());

            return challengeAnswerDTO;
        }
    }
}
