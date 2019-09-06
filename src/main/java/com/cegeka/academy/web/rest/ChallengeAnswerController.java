package com.cegeka.academy.web.rest;


import com.cegeka.academy.service.challengeAnswer.ChallengeAnswerService;
import com.cegeka.academy.service.dto.ChallengeAnswerDTO;
import com.cegeka.academy.web.rest.errors.ExistingItemException;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/challengeAnswer")
public class ChallengeAnswerController {

    @Autowired
    private ChallengeAnswerService challengeAnswerService;

    @PostMapping("/{userChallengeId}")
    public ResponseEntity<String> saveChallengeAnswer(@PathVariable(value = "userChallengeId") Long userChallengeId, @Valid @RequestBody ChallengeAnswerDTO challengeAnswerDTO) throws NotFoundException, ExistingItemException {

        challengeAnswerService.saveChallengeAnswer(userChallengeId, challengeAnswerDTO);

        return ResponseEntity.ok("Challenge answer has been saved.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateChallengeAnswer(@PathVariable(value = "id") Long id, @Valid @RequestBody ChallengeAnswerDTO challengeAnswerDTO) throws NotFoundException {

        challengeAnswerService.updateChallengeAnswer(id, challengeAnswerDTO);

        return ResponseEntity.ok("Challenge answer was updated.");

    }

    @DeleteMapping(value = "/{userId}/{challengeId}")
    public ResponseEntity<String> deleteChallengeAnswer(@PathVariable("userId") Long userId, @PathVariable("challengeId") Long challengeId) throws NotFoundException {

        challengeAnswerService.deleteChallengeAnswer(userId, challengeId);

        return ResponseEntity.ok("Challenge answer was deleted.");

    }

}


