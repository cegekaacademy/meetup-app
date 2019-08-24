package com.cegeka.academy.service.challenge;

import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.repository.ChallengeCategoryRepository;
import com.cegeka.academy.repository.ChallengeRepository;
import com.cegeka.academy.repository.UserChallengeRepository;
import com.cegeka.academy.web.rest.errors.InvalidFieldException;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.cegeka.academy.service.dto.ChallengeDTO;
import com.cegeka.academy.service.mapper.ChallengeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChallengeServiceImp implements ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private ChallengeCategoryRepository challengeCategoryRepository;

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
    public ChallengeDTO updateChallenge(Long challengeId, ChallengeDTO challengeDTO) throws NotFoundException {

        Long userId;

        if(challengeDTO.getCreator().getId() == null)
        {
            throw new InvalidFieldException().setMessage("Un challenge trebuie sa aiba un creator");
        }

        if(challengeDTO.getChallengeCategory() != null) {

            Optional<Long> challengeCategoryOptional = Optional.ofNullable(challengeDTO.getChallengeCategory().getId());

            if (!challengeCategoryOptional.isPresent()) {
                challengeDTO.setChallengeCategory(null);
            }

            challengeCategoryOptional.ifPresent(id -> challengeCategoryRepository.findById(id).
                    orElseThrow(() -> new InvalidFieldException().setMessage("Categoria aleasa trebuie sa existe"))
            );
        }

        if(!challengeId.equals(challengeDTO.getId()))
        {
            throw new InvalidFieldException().setMessage("Id-ul challenge-ului din path trebuie ca corespunda cu cel din DTO");
        }

        Challenge oldChallenge = challengeRepository.findById(challengeId).orElseThrow(
                ()->new NotFoundException().setMessage("Challenge-ul " + challengeId +" nu exista")
        );

        userId = challengeDTO.getCreator().getId();

        if(!oldChallenge.getCreator().getId().equals(userId))
        {
            throw new InvalidFieldException().setMessage("Creatorul unui challenge nu se poate schimba");
        }

        Challenge updatedChallenge = challengeRepository.save(ChallengeMapper.convertChallengeDTOToChallenge(challengeDTO));

        return ChallengeMapper.convertChallengeToChallengeDTO(updatedChallenge);
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

        if(challenges == null || challenges.isEmpty()){

            throw new NotFoundException().setMessage("List is empty.");
        }

       return challenges.stream().map(challenge -> ChallengeMapper.convertChallengeToChallengeDTO(challenge)).collect(Collectors.toList());

    }

    @Override
    public ChallengeDTO getChallengeById(long id) throws NotFoundException {

        Optional<Challenge> challengeOptional = challengeRepository.findById(id);

        challengeOptional.orElseThrow(
                () -> new NotFoundException().setMessage("Provocarea cu id-ul: " + id + " nu exista")
        );

        Challenge challenge = challengeOptional.get();

        return ChallengeMapper.convertChallengeToChallengeDTO(challenge);
    }

    private ChallengeDTO getChallengeDTOFromUserChallenge(UserChallenge userChallenge)
    {
        Challenge challenge = userChallenge.getChallenge();
        return ChallengeMapper.convertChallengeToChallengeDTO(challenge);
    }

}
