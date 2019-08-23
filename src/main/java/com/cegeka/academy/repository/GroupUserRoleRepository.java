package com.cegeka.academy.repository;

import com.cegeka.academy.domain.GroupUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupUserRoleRepository extends JpaRepository<GroupUserRole, Long> {

    List<GroupUserRole> findAllByGroupId(Long groupId);

}
