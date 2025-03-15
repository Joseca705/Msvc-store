package com.jose.store.infraestructure.exception;

public class ProductDoesNotExistException extends RuntimeException {

  public ProductDoesNotExistException() {
    super("The given product does not exist.");
  }
}
