package com.cegeka.academy.service.group;
import com.cegeka.academy.domain.User;

import java.util.List;

public interface GroupService {
    List<User> getAllUsersByGroup(Long groupId);
}
