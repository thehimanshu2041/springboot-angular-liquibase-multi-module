package com.trisul.core.exception;

import com.trisul.core.exception.exceptions.NotFoundException;
import com.trisul.core.exception.exceptions.ServiceException;
import com.trisul.core.exception.exceptions.UnAuthorisedException;
import com.trisul.core.exception.model.Error;
import com.trisul.core.exception.model.ErrorMessage;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionsHandler {

  /*
  Custom Exception Handler
   */
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ErrorMessage notFoundExceptionHandler(NotFoundException ex, WebRequest request) {
    ErrorMessage message =
        new ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            new Date(),
            new Error(ex.getMessage(), request.getDescription(false)));
    return message;
  }

  @ExceptionHandler(UnAuthorisedException.class)
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public ErrorMessage unAuthorisedExceptionHandler(UnAuthorisedException ex, WebRequest request) {
    ErrorMessage message =
        new ErrorMessage(
            HttpStatus.UNAUTHORIZED.value(),
            new Date(),
            new Error(ex.getMessage(), request.getDescription(false)));
    return message;
  }

  @ExceptionHandler(ServiceException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage serviceExceptionHandler(ServiceException ex, WebRequest request) {
    ErrorMessage message =
        new ErrorMessage(
            2041, new Date(), new Error(ex.getMessage(), request.getDescription(false)));
    return message;
  }

  /*
  Framework Exception Handler
   */
  @ExceptionHandler(AuthenticationException.class)
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public ErrorMessage authenticationExceptionHandler(AccessDeniedException ex, WebRequest request) {
    ErrorMessage message =
        new ErrorMessage(
            HttpStatus.UNAUTHORIZED.value(),
            new Date(),
            new Error(ex.getMessage(), request.getDescription(false)));
    return message;
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(value = HttpStatus.FORBIDDEN)
  public ErrorMessage accessDeniedExceptionHandler(AccessDeniedException ex, WebRequest request) {
    ErrorMessage message =
        new ErrorMessage(
            HttpStatus.FORBIDDEN.value(),
            new Date(),
            new Error(ex.getMessage(), request.getDescription(false)));
    return message;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorMessage methodArgumentNotValidExceptionHandler(
      MethodArgumentNotValidException ex, WebRequest request) {
    BindingResult bindingResult = ex.getBindingResult();
    ErrorMessage message =
        new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            new Date(),
            new Error(
                bindingResult.getFieldError().getDefaultMessage(), request.getDescription(false)));
    return message;
  }

  /*
  Global Exception Handler
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
    ErrorMessage message =
        new ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            new Date(),
            new Error(ex.getMessage(), request.getDescription(false)));
    return message;
  }
}
