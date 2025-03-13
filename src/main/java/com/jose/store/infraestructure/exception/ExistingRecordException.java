package com.jose.store.infraestructure.exception;

public class ExistingRecordException extends RuntimeException {

  public ExistingRecordException(String message) {
    super(message);
  }
}
