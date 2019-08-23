package com.cegeka.academy.web.rest;

import com.cegeka.academy.service.challenge.ChallengeService;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.springframework.web.bind.annotation.*;
import com.cegeka.academy.service.dto.ChallengeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ChallengeDTO updateChallengeController(@Valid @RequestBody ChallengeDTO challengeDTO) throws NotFoundException {
        return challengeService.updateChallenge(challengeDTO);
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
