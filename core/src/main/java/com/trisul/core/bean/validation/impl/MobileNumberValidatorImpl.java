package com.trisul.core.bean.validation.impl;

import com.trisul.core.bean.validation.MobileNumberValidator;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class MobileNumberValidatorImpl
    implements ConstraintValidator<MobileNumberValidator, String> {

  private final String MOBILE_NUMBER_REGEX = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";

  @Override
  public void initialize(MobileNumberValidator constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(String mobile, ConstraintValidatorContext constraintValidatorContext) {
    return Pattern.matches(MOBILE_NUMBER_REGEX, mobile);
  }
}
