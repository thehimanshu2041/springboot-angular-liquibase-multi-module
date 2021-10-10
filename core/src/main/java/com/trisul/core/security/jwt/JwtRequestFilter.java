package com.trisul.core.security.jwt;

import com.trisul.core.factory.MessageResource;
import com.trisul.core.logging.LoggerCodeCache;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired JwtTokenUtil jwtTokenUtil;

  @Autowired UserDetailsService userDetailsService;

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    final String requestTokenHeader = request.getHeader(SeqConstant.HEADER);
    String username = null;
    String jwtToken = jwtTokenUtil.getValidToken(requestTokenHeader);
    if (jwtToken != null) {
      try {
        username = jwtTokenUtil.getUsernameFromToken(jwtToken);
      } catch (IllegalArgumentException e) {
        LOGGER.error(MessageResource.getMessage(LoggerCodeCache.TCR_0001));
      } catch (ExpiredJwtException e) {
        LOGGER.error(MessageResource.getMessage(LoggerCodeCache.TCR_0002));
      } catch (Exception e) {
        LOGGER.error(MessageResource.getMessage(LoggerCodeCache.TCR_0003));
      }
    }
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (jwtTokenUtil.validateToken(jwtToken, username)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    chain.doFilter(request, response);
  }
}
