package com.cegeka.academy.service;

import com.cegeka.academy.domain.GroupUserRole;
import com.cegeka.academy.domain.Role;
import com.cegeka.academy.repository.GroupUserRoleRepository;
import com.cegeka.academy.service.dto.GroupDTO;
import com.cegeka.academy.service.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserGroupRolesService {

    @Autowired
    private GroupUserRoleRepository groupUserRoleRepository;

    public List<GroupUserRole> getById(Long userId){
        return groupUserRoleRepository.findAllByUserId(userId);
    }


}
