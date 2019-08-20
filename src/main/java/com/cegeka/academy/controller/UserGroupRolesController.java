package com.cegeka.academy.controller;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.domain.Group;
import com.cegeka.academy.domain.GroupUserRole;
import com.cegeka.academy.domain.Role;
import com.cegeka.academy.service.UserGroupRolesService;
import com.cegeka.academy.service.dto.GroupDTO;
import com.cegeka.academy.service.dto.GroupUserRoleDTO;
import com.cegeka.academy.service.dto.RoleDTO;
import com.cegeka.academy.service.dto.UserDTO;
import com.cegeka.academy.service.group.GroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/usergrouproles")
public class UserGroupRolesController {

    @Autowired
    private UserGroupRolesService userGroupRolesService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GroupService groupService;

    private GroupUserRoleDTO convertToDTO(GroupUserRole groupUserRole){
        GroupUserRoleDTO groupUserRoleDTO = modelMapper.map(groupUserRole, GroupUserRoleDTO.class);
        return groupUserRoleDTO;
    }


    private UserDTO convertToUserDTO(User user){
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    @GetMapping("/getGroups/{id}")
    public List<GroupUserRoleDTO> getGroupsByUserId(@PathVariable Long id){
        List<GroupUserRoleDTO> result = new ArrayList<>();
        List<GroupUserRole> list = userGroupRolesService.getById(id);
        for(GroupUserRole g : list){
            result.add(convertToDTO(g));
        }
        return result;
    }

    @GetMapping("getUsersByGroup/{id}")
    public List<UserDTO> getUsersByGroup(@PathVariable Long id)
    {
        List<User> users = groupService.getAllUsersByGroup(id);
        List<UserDTO> userDTOS = new ArrayList<>();

        for(User user : users)
        {
            userDTOS.add(convertToUserDTO((user)));
        }

        return userDTOS;
    }
}
