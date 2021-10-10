package com.trisul.data.access.dao.impl;

import com.trisul.core.security.jwt.RoleTypeEnum;
import com.trisul.data.access.dao.UserRoleDao;
import com.trisul.data.access.entity.UserRoleEntity;
import com.trisul.data.access.repository.UserRoleRepository;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleDaoImpl implements UserRoleDao {

  @Autowired UserRoleRepository userRoleRepository;

  @Override
  public Set<UserRoleEntity> findAllByNames(List<RoleTypeEnum> roles) {
    return userRoleRepository.findAllByNameIn(roles);
  }
}
