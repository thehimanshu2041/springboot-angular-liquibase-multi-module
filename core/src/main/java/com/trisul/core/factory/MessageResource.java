package com.trisul.core.factory;

import com.trisul.core.exception.FaultCode;
import com.trisul.core.logging.LoggerCode;
import java.util.Locale;
import java.util.MissingResourceException;

public class MessageResource {

  private MessageResource() {}

  public static String getMessage(final FaultCode faultCode) {
    if (faultCode == null) {
      return null;
    }
    String key = faultCode.getKey();
    try {
      return faultCode.getBundle().getMessage(key, null, null, Locale.ENGLISH);
    } catch (MissingResourceException missingResourceException) {
      String name = faultCode.getClass().getSimpleName();
      return "! Pair (code/message) missing in related bundle. Please provide a suitable message for the code : "
          + name
          + "."
          + key
          + " !";
    }
  }

  public static String getMessage(final LoggerCode loggerCode) {
    if (loggerCode == null) {
      return null;
    }
    String key = loggerCode.getKey();
    try {
      return loggerCode.getBundle().getMessage(key, null, null, Locale.ENGLISH);
    } catch (MissingResourceException missingResourceException) {
      String name = loggerCode.getClass().getSimpleName();
      return "! Pair (code/message) missing in related bundle. Please provide a suitable message for the code : "
          + name
          + "."
          + key
          + " !";
    }
  }
}
