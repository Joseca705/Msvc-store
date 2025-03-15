package com.jose.store.infraestructure.exception;

public class CodeNotValidException extends RuntimeException {

  public CodeNotValidException() {
    super("Code not valid");
  }
}
