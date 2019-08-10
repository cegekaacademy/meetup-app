package com.cegeka.academy.service.invitation;

import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.service.UserService;
import com.cegeka.academy.web.rest.errors.UnauthorizedUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public boolean verifyUserAccessForInvitationEntity(Long idInvitedUser){

        Optional<User> opt = userService.getUserWithAuthorities();
        final User[] userLogged = {null};
        opt.ifPresent(user -> userLogged[0] = user);

        Stream<BigInteger> user = invitationRepository.findInvitedUser(invitationRepository.findById(idInvitedUser));
        List<BigInteger> result = user.collect(Collectors.toList());

        if(result == null || result.size() == 0 || userLogged[0].getId() != Long.parseLong(result.get(0) + "")){

            return false;
        }

        return true;
    }
}
