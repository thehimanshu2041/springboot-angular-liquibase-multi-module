package com.trisul.internal.service.service;

import com.trisul.internal.service.model.AuthenticationDetail;
import com.trisul.internal.service.model.LoginDetail;
import com.trisul.internal.service.model.UserDetail;
import javax.servlet.http.HttpServletRequest;

public interface UserService {

  AuthenticationDetail doLogin(LoginDetail loginDetail);

  Boolean createUser(UserDetail userDetail);

  UserDetail getUserDetail();

  void trackRequest(HttpServletRequest httpServletRequest);
}
