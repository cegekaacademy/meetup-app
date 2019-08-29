package com.cegeka.academy.service.util;

import com.cegeka.academy.service.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

public class SortUtil {

    public static List<UserDTO> sortUsersByDate(List<UserDTO> users) {

        return users.stream().sorted((o1, o2) -> {
            if (o1.getCreatedDate() == null || o2.getCreatedDate() == null)
                return 0;
            int result;
            if (o1.getCreatedDate() == o2.getCreatedDate()) {
                result = 0;
            } else if (o1.getCreatedDate().isBefore(o2.getCreatedDate())) {
                result = 1;
            } else {
                result = -1;
            }
            return -1 * result;

        }).collect(Collectors.toList());
    }
}
