package com.cegeka.academy.web.rest;

import com.cegeka.academy.service.dto.UserChallengeDTO;
import com.cegeka.academy.service.userChallenge.UserChallengeService;
import com.cegeka.academy.web.rest.errors.InvalidInvitationStatusException;
import com.cegeka.academy.web.rest.errors.InvalidUserChallengeStatusException;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/updateStatus/{userChallengeId}/{status}")
    public ResponseEntity<String> updateUserChallengeStatus(@PathVariable(value = "userChallengeId") Long userChallengeId, @PathVariable(value = "status") String status) throws NotFoundException, InvalidUserChallengeStatusException {

        userChallengeService.updateUserChallengeStatus(userChallengeId, status);

        return ResponseEntity.ok("User challenge status was updated.");

    }

    @PutMapping("/updateInvitationStatus/{userChallengeId}/{invitationStatus}")
    public ResponseEntity<String> updateUserChallengeInvitationStatus(@PathVariable(value = "userChallengeId") Long userChallengeId, @PathVariable(value = "invitationStatus") String invitationStatus) throws NotFoundException, InvalidInvitationStatusException {

        userChallengeService.updateUserChallengeInvitationStatus(userChallengeId, invitationStatus);

        return ResponseEntity.ok("User challenge invitation status was updated.");

    }

}
