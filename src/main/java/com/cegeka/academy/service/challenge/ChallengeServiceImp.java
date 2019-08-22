package com.cegeka.academy.service.challenge;

import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.repository.ChallengeRepository;
import com.cegeka.academy.repository.GroupUserRoleRepository;
import com.cegeka.academy.repository.UserChallengeRepository;
import com.cegeka.academy.service.mapper.UserChallengeMapper;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.cegeka.academy.service.dto.ChallengeDTO;
import com.cegeka.academy.service.mapper.ChallengeMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChallengeServiceImp implements ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private UserChallengeRepository userChallengeRepository;

    @Override
    public void deleteChallenge(long id) throws NotFoundException {
            if(challengeRepository.findById(id).isPresent()) {
                challengeRepository.deleteById(id);
            }
            else {
                throw new NotFoundException().setMessage("Nu exista challenge cu id-ul: " + id);
            }
    }

    private Logger logger =  LoggerFactory.getLogger(ChallengeServiceImp.class);

    @Override
    public void saveChallenge(ChallengeDTO challengeDTO) {

        Challenge challenge = ChallengeMapper.convertChallengeDTOToChallenge(challengeDTO);

        logger.info("Challenge with id: " + challengeRepository.save(challenge).getId() + " has been saved");
        
    }

    @Override
    public Set<ChallengeDTO> getChallengesByUserId(long id) throws NotFoundException {

        List<UserChallenge> userChallengesList = userChallengeRepository.findAllByUserId(id);

        LinkedHashSet userChallengesSet = userChallengesList.stream()
                .map(userChallenge -> getChallengeDTOFromUserChallenge(userChallenge))
                .collect(Collectors.toCollection(LinkedHashSet::new));


        if(userChallengesSet.size() == 0)
        {
            throw new NotFoundException().setMessage("User-ul cu id-ul " + id + "nu a fost asociat cu nicio provocare");
        }

        return userChallengesSet;
    }

    @Override
    public List<ChallengeDTO> getChallengesByCreatorId(Long creatorId) throws NotFoundException {

        List<Challenge> challenges = challengeRepository.findAllByCreatorId(creatorId);

        if(challenges == null || challenges.size() == 0){

            throw new NotFoundException().setMessage("List is empty");

        }

        return challenges.stream().map(challenge -> ChallengeMapper.convertChallengeToChallengeDTO(challenge)).collect(Collectors.toList());

    }

    private ChallengeDTO getChallengeDTOFromUserChallenge(UserChallenge userChallenge)
    {
        Challenge challenge = userChallenge.getChallenge();
        return ChallengeMapper.convertChallengeToChallengeDTO(challenge);
    }

}
