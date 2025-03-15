package com.jose.store.infraestructure.exception;

public class ProviderDoesNotExistException extends RuntimeException {

  public ProviderDoesNotExistException() {
    super("The provider does not exist.");
  }
}
