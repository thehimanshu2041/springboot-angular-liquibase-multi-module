package com.trisul.core.exception.exceptions;

import com.trisul.core.exception.FaultCode;
import com.trisul.core.factory.MessageResource;

public class NotFoundException extends ExceptionResolver {

  private static final long serialVersionUID = 1L;

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(Exception exception) {
    super(exception);
  }

  public NotFoundException(FaultCode faultCode, Object... messageArguments) {
    super(MessageResource.getMessage(faultCode));
  }
}
