package com.cegeka.academy.web.rest;

import com.cegeka.academy.service.challenge.ChallengeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenge")
public class ChallengeController {

    @Autowired
    private ChallengeServiceImp challengeServiceImp;

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@RequestParam long id){
        challengeServiceImp.deleteChallenge(id);
    }

}
