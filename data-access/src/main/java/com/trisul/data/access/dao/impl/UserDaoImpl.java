package com.trisul.data.access.dao.impl;

import com.trisul.data.access.dao.UserDao;
import com.trisul.data.access.entity.IPEntity;
import com.trisul.data.access.entity.UserEntity;
import com.trisul.data.access.repository.IPRepository;
import com.trisul.data.access.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDaoImpl implements UserDao {

  @Autowired UserRepository userRepository;

  @Autowired IPRepository ipRepository;

  @Override
  public UserEntity findByUsername(String username) {
    return userRepository.findByUsername(username).orElse(null);
  }

  @Override
  public UserEntity findByEmail(String email) {
    return userRepository.findByEmail(email).orElse(null);
  }

  @Override
  public UserEntity save(UserEntity userEntity) {
    return userRepository.save(userEntity);
  }

  @Override
  public void saveTrackedRequest(IPEntity ipEntity) {
    ipRepository.save(ipEntity);
  }
}
