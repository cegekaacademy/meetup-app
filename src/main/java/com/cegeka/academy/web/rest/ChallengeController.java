package com.cegeka.academy.web.rest;

import com.cegeka.academy.service.challenge.ChallengeService;
import com.cegeka.academy.service.dto.ChallengeDTO;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/challenge")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ChallengeDTO getChallengeById(@PathVariable long id) throws NotFoundException {
        return challengeService.getChallengeById(id);
    }


    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) throws NotFoundException {
        challengeService.deleteChallenge(id);
    }

    @GetMapping(path = "/user/{id}")
    public Set<ChallengeDTO> getUserChallengesByUserId(@PathVariable long id) throws NotFoundException {
        return challengeService.getChallengesByUserId(id);
    }


    @PostMapping
    public void saveChallenge(@RequestBody ChallengeDTO challenge) {

        challengeService.saveChallenge(challenge);

    }
}
