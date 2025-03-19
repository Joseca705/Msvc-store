package com.jose.store.api.controller.error_handler;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.jose.store.api.model.response.ErrorResponse;
import com.jose.store.api.model.response.ErrorsResponse;
import com.jose.store.api.model.response.abstract_response.BaseErrorResponse;
import com.jose.store.infraestructure.exception.BatchStockDoesNotExistException;
import com.jose.store.infraestructure.exception.ExistingRecordException;
import com.jose.store.infraestructure.exception.ProductDoesNotExistException;
import com.jose.store.infraestructure.exception.ProviderDoesNotExistException;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestController {

  @ExceptionHandler(exception = MethodArgumentNotValidException.class)
  public BaseErrorResponse handleFailedValidation(
    MethodArgumentNotValidException exception
  ) {
    var errors = new ArrayList<String>();
    exception
      .getAllErrors()
      .forEach(error -> errors.add(error.getDefaultMessage()));
    return ErrorsResponse.builder()
      .errors(errors)
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }

  @ExceptionHandler(exception = ExistingRecordException.class)
  public BaseErrorResponse handleDuplicateRecordException(
    ExistingRecordException exception
  ) {
    return ErrorResponse.builder()
      .error(exception.getMessage())
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }

  @ExceptionHandler(exception = ProductDoesNotExistException.class)
  public BaseErrorResponse handleProductDoesNotExistException(
    ProductDoesNotExistException exception
  ) {
    return ErrorResponse.builder()
      .error(exception.getMessage())
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }

  @ExceptionHandler(exception = ProviderDoesNotExistException.class)
  public BaseErrorResponse handleProviderDoesNotExistException(
    ProviderDoesNotExistException exception
  ) {
    return ErrorResponse.builder()
      .error(exception.getMessage())
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }

  @ExceptionHandler(exception = BatchStockDoesNotExistException.class)
  public BaseErrorResponse handleBatchStockDoesNotExistException(
    BatchStockDoesNotExistException exception
  ) {
    return ErrorResponse.builder()
      .error(exception.getMessage())
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }

  @ExceptionHandler(exception = UnrecognizedPropertyException.class)
  public ErrorResponse handleUnrecognizedPropertyException(
    UnrecognizedPropertyException exception
  ) {
    return ErrorResponse.builder()
      .error("El modelo de datos enviado no es igual al modelo requerido.")
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }

  @ExceptionHandler(exception = MissingServletRequestParameterException.class)
  public ErrorResponse handleMissingServletRequestParameterException(
    MissingServletRequestParameterException exception
  ) {
    return ErrorResponse.builder()
      .error(exception.getMessage())
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }

  @ExceptionHandler(exception = MethodArgumentTypeMismatchException.class)
  public ErrorResponse handleMissingServletRequestParameterException(
    MethodArgumentTypeMismatchException exception
  ) {
    return ErrorResponse.builder()
      .error(exception.getMessage())
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }
}
