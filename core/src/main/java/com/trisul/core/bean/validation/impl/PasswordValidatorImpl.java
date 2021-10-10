package com.trisul.core.bean.validation.impl;

import com.trisul.core.bean.validation.PasswordValidator;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidatorImpl implements ConstraintValidator<PasswordValidator, String> {

  private final String PASSWORD_REGEX =
      "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";

  @Override
  public void initialize(PasswordValidator constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
    return Pattern.matches(PASSWORD_REGEX, password);
  }
}
