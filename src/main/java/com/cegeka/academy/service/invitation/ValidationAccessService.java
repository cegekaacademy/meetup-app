package com.cegeka.academy.service.invitation;

import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ValidationAccessService {

    private final UserService userService;
    private final InvitationRepository invitationRepository;

    @Autowired
    public ValidationAccessService(UserService userService, InvitationRepository invitationRepository) {
        this.userService = userService;
        this.invitationRepository = invitationRepository;
    }

    public boolean verifyUserAccessForInvitationEntity(Long invitationId){

        if(invitationId == null){

            return false;

        }else {

            Optional<User> opt = userService.getUserWithAuthorities();
            final User[] userLogged = {null};

            opt.ifPresent(user -> userLogged[0] = user);

            if (userLogged[0] == null) {

                return false;

            } else {

                User invitedUser = invitationRepository.findById(invitationId).get().getUser();

                if (invitedUser == null || userLogged != null || !userLogged[0].equals(invitedUser)) {

                    return false;
                }
            }

        }

        return true;
    }
}
