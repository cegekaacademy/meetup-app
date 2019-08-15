package com.cegeka.academy.web.rest;

import com.cegeka.academy.service.challenge.ChallengeServiceImp;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/challenge")
public class ChallengeController {

    @Autowired
    private ChallengeServiceImp challengeServiceImp;

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) throws NotFoundException {
        challengeServiceImp.deleteChallenge(id);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> handle(NotFoundException ex)
    {
        return new ResponseEntity<String>("Challange-ul cu id-ul selectat nu exista", HttpStatus.NOT_FOUND);
    }

}
