package com.cegeka.academy.web.rest;

import com.cegeka.academy.service.dto.UserChallengeDTO;
import com.cegeka.academy.service.userChallenge.UserChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userChallenge")
public class UserChallengeController {

    @Autowired
    private UserChallengeService userChallengeService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserChallengeDTO> getChallengesByUserId(@PathVariable("id") Long id){

        return userChallengeService.getUserChallengesByUserId(id);
    }

}
