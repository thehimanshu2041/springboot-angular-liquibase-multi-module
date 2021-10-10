package com.trisul.data.access.dao;

import com.trisul.core.security.jwt.RoleTypeEnum;
import com.trisul.data.access.entity.UserRoleEntity;
import java.util.List;
import java.util.Set;

public interface UserRoleDao {

  Set<UserRoleEntity> findAllByNames(List<RoleTypeEnum> roles);
}
