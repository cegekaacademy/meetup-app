package com.cegeka.academy.web.rest;

import com.cegeka.academy.service.challenge.ChallengeServiceImp;
import com.cegeka.academy.service.dto.ChallengeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenge")
public class ChallengeController {

    @Autowired
    ChallengeServiceImp challengeService;

    @PostMapping
    public void saveChallenge(@RequestBody ChallengeDTO challenge) {

        challengeService.saveChallenge(challenge);

    }
}
