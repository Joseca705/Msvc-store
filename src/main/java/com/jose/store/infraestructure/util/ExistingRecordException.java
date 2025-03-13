package com.jose.store.infraestructure.util;

public class ExistingRecordException extends RuntimeException {

  public ExistingRecordException(String message) {
    super(message);
  }
}
