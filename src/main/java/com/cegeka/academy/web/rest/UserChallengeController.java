package com.cegeka.academy.web.rest;

import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.service.dto.UserChallengeDTO;
import com.cegeka.academy.service.userChallenge.UserChallengeService;
import com.cegeka.academy.web.rest.errors.WrongOwnerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userChallenge")
public class UserChallengeController {

    @Autowired
    private UserChallengeService userChallengeService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public List<UserChallengeDTO> getChallengesByUserId(@PathVariable("userId") Long userId){

        return userChallengeService.getUserChallengesByUserId(userId);
    }

    @PutMapping(value = "/rate")
    public UserChallenge rateUser(@RequestBody UserChallengeDTO userChallengeDTO,
                                  @Param("ownerId") Long ownerId) throws WrongOwnerException {

        return userChallengeService.rateUser(userChallengeDTO, ownerId);
    }

}
