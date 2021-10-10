package com.trisul.core.logging;

import java.io.Serializable;
import org.springframework.context.MessageSource;

public interface LoggerCode extends Serializable {

  String getKey();

  MessageSource getBundle();
}
