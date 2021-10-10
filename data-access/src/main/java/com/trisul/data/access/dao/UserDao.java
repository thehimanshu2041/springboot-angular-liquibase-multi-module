package com.trisul.data.access.dao;

import com.trisul.data.access.entity.IPEntity;
import com.trisul.data.access.entity.UserEntity;

public interface UserDao {

  UserEntity findByUsername(String username);

  UserEntity findByEmail(String email);

  UserEntity save(UserEntity userEntity);

  void saveTrackedRequest(IPEntity ipEntity);
}
