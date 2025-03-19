package com.jose.store.infraestructure.exception;

public class ProviderDoesNotExistException extends RuntimeException {

  public ProviderDoesNotExistException() {
    super("El proveedor no existe.");
  }
}
