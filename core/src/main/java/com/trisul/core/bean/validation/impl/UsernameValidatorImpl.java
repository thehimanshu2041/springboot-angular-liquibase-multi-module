package com.trisul.core.bean.validation.impl;

import com.trisul.core.bean.validation.UsernameValidator;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UsernameValidatorImpl implements ConstraintValidator<UsernameValidator, String> {

  private final String USERNAME_REGEX = "^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$";

  @Override
  public void initialize(UsernameValidator constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
    return Pattern.matches(USERNAME_REGEX, username);
  }
}
