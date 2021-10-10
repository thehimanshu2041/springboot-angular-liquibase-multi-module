package com.trisul.internal.service.service.impl;

import com.trisul.core.exception.FaultCodeCache;
import com.trisul.core.exception.exceptions.NotFoundException;
import com.trisul.core.exception.exceptions.ServiceException;
import com.trisul.core.security.jwt.JwtTokenUtil;
import com.trisul.core.security.jwt.RoleTypeEnum;
import com.trisul.core.security.jwt.SeqConstant;
import com.trisul.core.security.user.UserStore;
import com.trisul.data.access.dao.StaticDataDao;
import com.trisul.data.access.dao.UserDao;
import com.trisul.data.access.dao.UserRoleDao;
import com.trisul.data.access.entity.IPEntity;
import com.trisul.data.access.entity.UserEntity;
import com.trisul.data.access.entity.UserRoleEntity;
import com.trisul.internal.service.mapper.UserMapper;
import com.trisul.internal.service.model.AuthenticationDetail;
import com.trisul.internal.service.model.LoginDetail;
import com.trisul.internal.service.model.UserDetail;
import com.trisul.internal.service.service.ResolverService;
import com.trisul.internal.service.service.UserService;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  @Autowired UserDao userDao;

  @Autowired UserRoleDao userRoleDao;

  @Autowired StaticDataDao staticDataDao;

  @Autowired JwtTokenUtil jwtTokenUtil;

  @Autowired UserMapper userMapper;

  @Autowired UserStore userStore;

  @Autowired ResolverService resolverService;

  @Override
  public UserDetails loadUserByUsername(String username) {
    UserEntity user = userDao.findByUsername(username);
    if (user == null) {
      throw new NotFoundException(FaultCodeCache.TIS_0002);
    }
    Set<SimpleGrantedAuthority> grantedAuthorities = getAuthorities(user);
    return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
  }

  @Override
  public AuthenticationDetail doLogin(LoginDetail loginDetail) {
    UserEntity user = userDao.findByUsername(loginDetail.getUsername());
    if (null == user) {
      throw new NotFoundException(FaultCodeCache.TIS_0002);
    }
    if (user.getUsername().equals(loginDetail.getUsername())
        && checkPassword(loginDetail.getPassword(), user.getPassword())) {
      return new AuthenticationDetail(
          jwtTokenUtil.generateToken(loginDetail.getUsername()), getUserRoles(user.getUserRoles()));
    }
    throw new ServiceException(FaultCodeCache.TIS_0006);
  }

  @Override
  public Boolean createUser(UserDetail userDetail) {
    if (null != userDao.findByUsername(userDetail.getUsername())) {
      throw new ServiceException(FaultCodeCache.TIS_0003);
    }
    if (null != userDao.findByEmail(userDetail.getEmail())) {
      throw new ServiceException(FaultCodeCache.TIS_0004);
    }
    if (null != userDetail.getTitle()) {
      userDetail.setTitle(resolverService.resolveCodeDetail(userDetail.getTitle()));
    }
    if (null != userDetail.getGender()) {
      userDetail.setGender(resolverService.resolveCodeDetail(userDetail.getGender()));
    }

    if (null != userDetail.getAddressDetail()) {
      if (null != userDetail.getAddressDetail().getAddressCity()) {
        userDetail
            .getAddressDetail()
            .setAddressCity(
                resolverService.resolveCscDetail(userDetail.getAddressDetail().getAddressCity()));
      }
      if (null != userDetail.getAddressDetail().getAddressState()) {
        userDetail
            .getAddressDetail()
            .setAddressState(
                resolverService.resolveCscDetail(userDetail.getAddressDetail().getAddressState()));
      }
      if (null != userDetail.getAddressDetail().getAddressCountry()) {
        userDetail
            .getAddressDetail()
            .setAddressCountry(
                resolverService.resolveCscDetail(
                    userDetail.getAddressDetail().getAddressCountry()));
      }
    }

    UserEntity userEntity = userMapper.convertUserDetailToUserEntity(userDetail);
    userEntity.getAddressEntity().setUserEntity(userEntity);
    userEntity.getCardEntity().setUserEntity(userEntity);
    userEntity.setPassword(BCrypt.hashpw(userDetail.getPassword(), BCrypt.gensalt()));
    userEntity.setUserRoles(
        userRoleDao.findAllByNames(Collections.singletonList(RoleTypeEnum.USER)));
    userDao.save(userEntity);
    return true;
  }

  @Override
  public UserDetail getUserDetail() {
    UserEntity userEntity = userDao.findByUsername(userStore.getLoggedInUser());
    if (null == userEntity) {
      throw new ServiceException(FaultCodeCache.TIS_0007);
    }
    return userMapper.convertUserEntityToUserDetail(
        userEntity,
        staticDataDao.getCodeEntityByCodeId(userEntity.getTitle()),
        staticDataDao.getCodeEntityByCodeId(userEntity.getGender()),
        staticDataDao.getCityByCityId(userEntity.getAddressEntity().getAddressCity()),
        staticDataDao.getStateByStateId(userEntity.getAddressEntity().getAddressState()),
        staticDataDao.getCountryByCountryId(userEntity.getAddressEntity().getAddressCountry()));
  }

  @Override
  public void trackRequest(HttpServletRequest request) {
    IPEntity ipEntity = new IPEntity();
    String token = jwtTokenUtil.getValidToken(request.getHeader(SeqConstant.HEADER));
    ipEntity.setIpToken(token);
    ipEntity.setIpUsername(null != token ? jwtTokenUtil.getUsernameFromToken(token) : null);
    ipEntity.setIpPort(Integer.toString(request.getServerPort()));
    ipEntity.setIpContextPath(request.getContextPath());
    ipEntity.setIpRequestPath(request.getRequestURI());
    ipEntity.setIpAddress(null);
    ipEntity.setIpMethod(request.getMethod());
    ipEntity.setIpPostalCode(null);
    ipEntity.setIpLatitude(null);
    ipEntity.setIpLongitude(null);
    ipEntity.setIpCity(null);
    ipEntity.setIpState(null);
    ipEntity.setIpCountry(null);
    ipEntity.setIpMetroCode(null);
    ipEntity.setIpAreaCode(null);
    ipEntity.setIpNum(null);
    ipEntity.setIpURL(null);
    ipEntity.setIpReferer(null);
    userDao.saveTrackedRequest(ipEntity);
  }

  private Set<SimpleGrantedAuthority> getAuthorities(UserEntity user) {
    Set<UserRoleEntity> roleByUserId = user.getUserRoles();
    return roleByUserId.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toString().toUpperCase()))
        .collect(Collectors.toSet());
  }

  private Set<String> getUserRoles(Set<UserRoleEntity> userRoleEntities) {
    Set<String> roles = new HashSet<>();
    userRoleEntities.stream()
        .map(m -> roles.add(m.getName().getCode()))
        .collect(Collectors.toList());
    return roles;
  }

  private static boolean checkPassword(String password_plaintext, String stored_hash) {
    boolean password_verified;
    if (null == stored_hash || !stored_hash.startsWith("$2a$")) {
      throw new ServiceException(FaultCodeCache.TIS_0005);
    }
    password_verified = BCrypt.checkpw(password_plaintext, stored_hash);
    return (password_verified);
  }
}
