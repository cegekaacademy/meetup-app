package com.cegeka.academy.service.challenge;

import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.repository.ChallengeRepository;
import com.cegeka.academy.repository.GroupUserRoleRepository;
import com.cegeka.academy.repository.UserChallengeRepository;
import com.cegeka.academy.web.rest.errors.EmptyChallengeSetException;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.cegeka.academy.service.dto.ChallengeDTO;
import com.cegeka.academy.service.mapper.ChallengeMapper;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
@Transactional
public class ChallengeServiceImp implements ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private GroupUserRoleRepository groupUserRoleRepository;

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
    public Set<ChallengeDTO> getChallengesByUserId(long id) throws EmptyChallengeSetException {

        Set<ChallengeDTO> userChallengesSet = new LinkedHashSet<>();

        for(UserChallenge userChallenge : userChallengeRepository.findAllByUserId(id))
        {
            Challenge challenge = userChallenge.getChallenge();
            userChallengesSet.add(ChallengeMapper.convertChallengeToChallengeDTO(challenge));
        }

        if(userChallengesSet.size() == 0)
        {
            throw new EmptyChallengeSetException().setMessage("User-ul cu id-ul " + id + "nu a fost asociat cu nicio provocare");
        }

        return userChallengesSet;
    }


}
