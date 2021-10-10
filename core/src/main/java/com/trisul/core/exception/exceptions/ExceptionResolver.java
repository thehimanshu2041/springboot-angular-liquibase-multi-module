package com.trisul.core.exception.exceptions;

import com.trisul.core.exception.FaultCode;

public class ExceptionResolver extends RuntimeException {

  protected FaultCode faultCode;
  protected Object[] messageArguments;

  public ExceptionResolver(String message) {
    super(message);
  }

  public ExceptionResolver(String message, Throwable cause) {
    super(message, cause);
  }

  public ExceptionResolver(Throwable cause) {
    super(cause);
  }

  public ExceptionResolver(ExceptionResolver cause) {
    this(cause.getCause(), cause.getFaultCode(), cause.getMessageArguments());
  }

  public ExceptionResolver(Throwable cause, FaultCode faultCode, Object... messageArguments) {
    super(cause);
    this.faultCode = faultCode;
    this.messageArguments = messageArguments;
  }

  public ExceptionResolver(FaultCode faultCode, Object... messageArguments) {
    this((Throwable) null, faultCode, messageArguments);
  }

  public FaultCode getFaultCode() {
    return this.faultCode;
  }

  public Object[] getMessageArguments() {
    return this.messageArguments;
  }
}
