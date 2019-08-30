package com.cegeka.academy.service.util;

import com.cegeka.academy.service.dto.UserDTO;
import com.cegeka.academy.service.invitation.InvitationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class SortUtil {

    private static Logger logger = LoggerFactory.getLogger(InvitationServiceImpl.class);


    public static List<UserDTO> sortUsersByDate(List<UserDTO> users) {

        return users.stream().sorted((o1, o2) -> {

            if (o1.getCreatedDate() == null && o2.getCreatedDate() == null) {

                logger.info("Both data are null");
                return 0;

            } else if (o1.getCreatedDate() == null) {

                logger.info("First date is null");
                return 1;

            } else if (o2.getCreatedDate() == null) {

                logger.info("Second date is null");
                return -1;
            }

            return -1 * o1.getCreatedDate().compareTo(o2.getCreatedDate());

        }).collect(Collectors.toList());
    }
}
