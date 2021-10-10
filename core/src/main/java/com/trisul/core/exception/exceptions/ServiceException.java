package com.trisul.core.exception.exceptions;

import com.trisul.core.exception.FaultCode;
import com.trisul.core.factory.MessageResource;

public class ServiceException extends ExceptionResolver {

  private static final long serialVersionUID = 1L;

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(Exception exception) {
    super(exception);
  }

  public ServiceException(FaultCode faultCode, Object... messageArguments) {
    super(MessageResource.getMessage(faultCode));
  }
}
