package com.trisul.core.exception;

import java.io.Serializable;
import org.springframework.context.MessageSource;

public interface FaultCode extends Serializable {

  String getKey();

  MessageSource getBundle();
}
