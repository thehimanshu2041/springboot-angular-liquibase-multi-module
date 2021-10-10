package com.trisul.data.access.repository;

import com.trisul.core.security.jwt.RoleTypeEnum;
import com.trisul.data.access.entity.UserRoleEntity;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

  Set<UserRoleEntity> findAllByNameIn(List<RoleTypeEnum> roles);
}
