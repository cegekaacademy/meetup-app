package com.cegeka.academy.service.challenge;

import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.repository.ChallengeRepository;
import com.cegeka.academy.repository.GroupUserRoleRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import com.cegeka.academy.service.dto.ChallengeDTO;
import com.cegeka.academy.service.mapper.ChallengeMapper;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;

@Service
@Transactional
public class ChallengeServiceImp implements ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private GroupUserRoleRepository groupUserRoleRepository;

    @Override
    public void deleteChallenge(long id) throws NotFoundException {
            if(challengeRepository.findById(id).isPresent()) {
                challengeRepository.deleteById(id);
            }
            else {
                throw new NotFoundException("");
            }
    }

    private Logger logger =  LoggerFactory.getLogger(ChallengeServiceImp.class);

    @Override
    public void saveChallenge(ChallengeDTO challengeDTO) {

        Challenge challenge = ChallengeMapper.convertChallengeDTOToChallenge(challengeDTO);

        logger.info("Challenge with id: " + challengeRepository.save(challenge).getId() + " has been saved");
    }
}
