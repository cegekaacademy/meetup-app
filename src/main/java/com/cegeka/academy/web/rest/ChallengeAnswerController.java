package com.cegeka.academy.web.rest;


import com.cegeka.academy.service.challengeAnswer.ChallengeAnswerService;
import com.cegeka.academy.service.dto.ChallengeAnswerDTO;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequestMapping("/challengeAnswer")
public class ChallengeAnswerController {

    @Autowired
    private ChallengeAnswerService challengeAnswerService;

    @PostMapping
    public ResponseEntity<String> saveChallengeAnswer(@Valid @RequestBody ChallengeAnswerDTO challengeAnswerDTO){

        challengeAnswerService.saveChallengeAnswer(challengeAnswerDTO);

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

    @PostMapping(value = "/{challengeAnswerId}")
    public ResponseEntity<String> uploadAnswerPhoto(@PathVariable("challengeAnswerId") Long challengeAnswerId,
                                               @ModelAttribute ChallengeAnswerDTO challengeAnswerDTO) throws IOException, NotFoundException {

        challengeAnswerService.uploadAnswerPhoto(challengeAnswerId, challengeAnswerDTO);

        return ResponseEntity.ok("Answer has been uploaded.");
    }

}


