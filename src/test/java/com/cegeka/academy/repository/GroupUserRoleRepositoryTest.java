package com.cegeka.academy.repository;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.Group;
import com.cegeka.academy.domain.GroupUserRole;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.repository.util.TestsRepositoryUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class GroupUserRoleRepositoryTest {

    @Autowired
    private GroupUserRoleRepository groupUserRoleRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    private User user1, user2;
    private Group group;
    private GroupUserRole groupUserRole1, groupUserRole2;

    @BeforeEach
    public void init() {

        user1 = TestsRepositoryUtil.createUser("login1", "1aaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.saveAndFlush(user1);
        user2 = TestsRepositoryUtil.createUser("login2", "2ananaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaanaana");
        userRepository.saveAndFlush(user2);
        group = TestsRepositoryUtil.createGroup("gr1", "ana are mere");
        groupRepository.save(group);
        groupUserRole1 = TestsRepositoryUtil.createGroupUserRole(user1, groupRepository.findAll().get(0), null);
        groupUserRoleRepository.save(groupUserRole1);
        groupUserRole2 = TestsRepositoryUtil.createGroupUserRole(user2, groupRepository.findAll().get(0), null);
        groupUserRoleRepository.save(groupUserRole2);
    }

    @Test
    public void testFindAllByGroupId() {

        Long idGroup = groupRepository.findAll().get(0).getId();
        List<GroupUserRole> list = groupUserRoleRepository.findAllByGroupId(idGroup);
        assertThat(list.size()).isEqualTo(2);
    }

}
