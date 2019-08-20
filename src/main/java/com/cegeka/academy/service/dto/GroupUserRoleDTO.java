package com.cegeka.academy.service.dto;

import com.cegeka.academy.domain.Group;
import com.cegeka.academy.domain.Role;
import com.cegeka.academy.domain.User;

public class GroupUserRoleDTO {

    private long id;
    private User user;
    private Group group;
    private Role role;

    public GroupUserRoleDTO(){

    }

    public GroupUserRoleDTO(long id, User user, Group group, Role role) {
        this.id = id;
        this.user = user;
        this.group = group;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
