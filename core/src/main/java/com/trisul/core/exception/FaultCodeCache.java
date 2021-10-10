package com.trisul.core.exception;

import org.springframework.context.support.ResourceBundleMessageSource;

public enum FaultCodeCache implements FaultCode {

  // INTERNAL-SERVICE
  TIS_0001,
  TIS_0002,
  TIS_0003,
  TIS_0004,
  TIS_0005,
  TIS_0006,
  TIS_0007;

  private static final String BUNDLE_NAME = "i18n.exception";

  @Override
  public String getKey() {
    return toString();
  }

  @Override
  public ResourceBundleMessageSource getBundle() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename(BUNDLE_NAME);
    return messageSource;
  }
}
