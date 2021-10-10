package com.trisul.core.logging;

import org.springframework.context.support.ResourceBundleMessageSource;

public enum LoggerCodeCache implements LoggerCode {
  // CORE
  TCR_0001,
  TCR_0002,
  TCR_0003,

  // INTERNAL-SERVICE
  TIS_0001;

  private static final String BUNDLE_NAME = "i18n.message";

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
