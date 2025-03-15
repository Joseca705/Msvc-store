package com.jose.store.infraestructure.exception;

public class BatchStockDoesNotExistException extends RuntimeException {

  public BatchStockDoesNotExistException() {
    super("The batch does not exist.");
  }
}
