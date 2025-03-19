package com.jose.store.infraestructure.exception;

public class ProductDoesNotExistException extends RuntimeException {

  public ProductDoesNotExistException() {
    super("El producto no existe.");
  }
}
