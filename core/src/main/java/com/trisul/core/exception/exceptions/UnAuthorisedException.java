package com.trisul.core.exception.exceptions;

import com.trisul.core.exception.FaultCode;
import com.trisul.core.factory.MessageResource;

public class UnAuthorisedException extends ExceptionResolver {

  private static final long serialVersionUID = 1L;

  public UnAuthorisedException(String message) {
    super(message);
  }

  public UnAuthorisedException(Exception exception) {
    super(exception);
  }

  public UnAuthorisedException(FaultCode faultCode, Object... messageArguments) {
    super(MessageResource.getMessage(faultCode));
  }
}
