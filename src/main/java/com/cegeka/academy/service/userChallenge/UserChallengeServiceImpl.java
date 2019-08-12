package com.cegeka.academy.service.userChallenge;

import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.repository.UserChallengeRepository;
import com.cegeka.academy.service.dto.UserChallengeDTO;
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

        return userChallengeList.stream().map(userChallenge -> convertToDTO(userChallenge)).collect(Collectors.toList());

    }

    public static UserChallenge convertToEntity(UserChallengeDTO userChallengeDTO){

        if(userChallengeDTO != null){

            UserChallenge userChallenge = new UserChallenge();
            userChallenge.setId(userChallengeDTO.getId());
            userChallenge.setUser(userChallengeDTO.getUser());
            userChallenge.setInvitation(userChallengeDTO.getInvitation());
            userChallenge.setChallenge(userChallengeDTO.getChallenge());
            userChallenge.setStatus(userChallengeDTO.getStatus());
            userChallenge.setPoints(userChallengeDTO.getPoints());
            userChallenge.setStartTime(userChallengeDTO.getStartTime());
            userChallenge.setEndTime(userChallengeDTO.getEndTime());

            return userChallenge;
        }

        return null;
    }

    public UserChallengeDTO convertToDTO(UserChallenge userChallenge){

        if(userChallenge != null){

            UserChallengeDTO userChallengeDTO = new UserChallengeDTO();
            userChallengeDTO.setId(userChallenge.getId());
            userChallengeDTO.setUser(userChallenge.getUser());
            userChallengeDTO.setInvitation(userChallenge.getInvitation());
            userChallengeDTO.setChallenge(userChallenge.getChallenge());
            userChallengeDTO.setStatus(userChallenge.getStatus());
            userChallengeDTO.setPoints(userChallenge.getPoints());
            userChallengeDTO.setStartTime(userChallenge.getStartTime());
            userChallengeDTO.setEndTime(userChallenge.getEndTime());

            return userChallengeDTO;
        }

        return null;
    }

}
