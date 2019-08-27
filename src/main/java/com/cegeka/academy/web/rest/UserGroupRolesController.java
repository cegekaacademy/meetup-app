package com.cegeka.academy.web.rest;
        import com.cegeka.academy.domain.User;
        import com.cegeka.academy.domain.GroupUserRole;
        import com.cegeka.academy.service.groupUserRole.UserGroupRolesServiceImpl;
        import com.cegeka.academy.service.dto.GroupUserRoleDTO;
        import com.cegeka.academy.service.dto.UserDTO;
        import com.cegeka.academy.service.group.GroupService;
        import org.modelmapper.ModelMapper;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;

        import javax.validation.Valid;
        import java.util.ArrayList;
        import java.util.List;

@RestController
@RequestMapping("/usergrouproles")
public class UserGroupRolesController {

    @Autowired
    private UserGroupRolesServiceImpl userGroupRolesService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GroupService groupService;

//    private GroupUserRoleDTO convertToDTO(GroupUserRole groupUserRole){
//        GroupUserRoleDTO groupUserRoleDTO = modelMapper.map(groupUserRole, GroupUserRoleDTO.class);
//        return groupUserRoleDTO;
//    }
//
//
//    private UserDTO convertToUserDTO(User user){
//        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
//        return userDTO;
//    }

//    @GetMapping("/getGroups/{id}")
//    public List<GroupUserRoleDTO> getGroupsByUserId(@PathVariable Long id){
//        List<GroupUserRoleDTO> result = new ArrayList<>();
//        List<GroupUserRole> list = userGroupRolesService.getById(id);
//        for(GroupUserRole g : list){
//            result.add(convertToDTO(g));
//        }
//        return result;
//    }

//    @GetMapping("getUsersByGroup/{id}")
//    public List<UserDTO> getUsersByGroup(@PathVariable Long id)
//    {
//        List<User> users = groupService.getAllUsersByGroup(id);
//        List<UserDTO> userDTOS = new ArrayList<>();
//
//        for(User user : users)
//        {
//            userDTOS.add(convertToUserDTO((user)));
//        }
//
//        return userDTOS;
//    }

    @PostMapping("addMember")
    public void addMember(@Valid @RequestBody GroupUserRole user){
        userGroupRolesService.addMember(user);
    }

    @DeleteMapping("deleteMember/{groupId}/{userId}")
    public void removeMember(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId){
        userGroupRolesService.removeMember(groupId, userId);
    }
}
