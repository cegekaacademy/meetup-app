package com.cegeka.academy.web.rest;

import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.springframework.web.bind.annotation.*;
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
    private ChallengeServiceImp challengeServiceImp;

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) throws NotFoundException {
        challengeServiceImp.deleteChallenge(id);
    }

    @PostMapping
    public void saveChallenge(@RequestBody ChallengeDTO challenge) {

        challengeServiceImp.saveChallenge(challenge);

    }
}
