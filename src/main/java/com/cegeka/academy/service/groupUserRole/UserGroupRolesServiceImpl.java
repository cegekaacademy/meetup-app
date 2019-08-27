package com.cegeka.academy.service.groupUserRole;

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
public class UserGroupRolesServiceImpl {

    @Autowired
    private GroupUserRoleRepository groupUserRoleRepository;

    public List<GroupUserRole> getById(Long userId){
        return groupUserRoleRepository.findAllByUserId(userId);
    }

    public void addMember(GroupUserRole user){
        groupUserRoleRepository.save(user);
    }
    public void removeMember(Long groupId, Long userId){
        GroupUserRole gur = groupUserRoleRepository.findOneByGroupIdAndUserId(groupId, userId);
        groupUserRoleRepository.delete(gur);
    }



}
