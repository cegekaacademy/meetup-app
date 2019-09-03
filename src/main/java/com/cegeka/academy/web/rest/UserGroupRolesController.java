package com.cegeka.academy.web.rest;
        import com.cegeka.academy.domain.UserStatistics;
        import com.cegeka.academy.service.dto.*;
        import com.cegeka.academy.domain.Group;
        import com.cegeka.academy.domain.GroupUserRole;
        import com.cegeka.academy.service.groupUserRole.UserGroupRolesServiceImpl;
        import com.cegeka.academy.service.group.GroupService;
        import com.cegeka.academy.service.userStatistics.UserStatisticsService;
        import org.modelmapper.ModelMapper;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;
        import com.cegeka.academy.domain.User;

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
    private UserStatisticsService userStatisticsService;

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

    private GroupDTO convertToGroupDto(Group group){
    GroupDTO groupDTO = modelMapper.map(group, GroupDTO.class);
    return groupDTO;
    }

    @GetMapping("/getGroups")
    public List<GroupUserRoleDTO> getGroupsByUserId(@Valid @RequestBody UserIdDTO userIdDTO){
        List<GroupUserRoleDTO> result = new ArrayList<>();
        List<GroupUserRole> list = userGroupRolesService.getById(userIdDTO.id);
        for(GroupUserRole g : list){
            result.add(convertToDTO(g));
        }
        return result;
    }


    @GetMapping("getStatistics")
    public UserStatistics getUserStatistics(@Valid @RequestBody GetUserStatisticsDTO userDTO){
        return userStatisticsService.getUserStatistics(userDTO.age, userDTO.gender);
    }

    @GetMapping("getUsersByGroup")
    public List<ReturnUserDTO> getUsersByGroup(@Valid @RequestBody GetPossibleGroupsDTO groupDTO)
    {
        List<User> users = groupService.getAllUsersByGroup(groupDTO.id);
        List<ReturnUserDTO> userDTOS = new ArrayList<>();

        for(User user : users)
        {
            userDTOS.add(new ReturnUserDTO(user.getFirstName(), user.getLastName(), user.getEmail()));
        }

        return userDTOS;
    }

    @GetMapping ("groups")
    public List<GroupDTO> getPossibleGroupsForUser(@Valid @RequestBody GetPossibleGroupsDTO groupsDTO)
    {
        List<GroupUserRole> possibleGroups = userGroupRolesService.findGroupsUserNotMember(groupsDTO.id);
        List<GroupDTO> groupDTOS = new ArrayList<>();

        List<Group> getGroupss = new ArrayList<>();

        for(GroupUserRole groupUserRole : possibleGroups)
        {
            getGroupss.add(groupService.findGroupById(groupUserRole.getId()));
        }

        for(Group group : getGroupss)
        {
            groupDTOS.add(convertToGroupDto((group)));
        }

        return groupDTOS;

    }


    @PostMapping("addMember")
    public void addMember(@Valid @RequestBody GroupUserRole user){
        userGroupRolesService.addMember(user);
    }
//
//    @DeleteMapping("deleteMember/{groupId}/{userId}")
//    public void removeMember(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId){
//        userGroupRolesService.removeMember(groupId, userId);


//    }
}
