package com.trisul.core.security.jwt;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

  private static final long serialVersionUID = 1L;

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response
        .getOutputStream()
        .println(
            "{\"status\":"
                + HttpServletResponse.SC_UNAUTHORIZED
                + ",\"timestamp\":\""
                + LocalDateTime.now()
                + "\",\"error\":{\"message\":"
                + authException.getLocalizedMessage()
                + ",\"description\":\""
                + authException.getMessage()
                + "\"}}");
  }
}
