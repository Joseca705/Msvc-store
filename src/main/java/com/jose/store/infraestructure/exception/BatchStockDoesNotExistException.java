package com.jose.store.infraestructure.exception;

public class BatchStockDoesNotExistException extends RuntimeException {

  public BatchStockDoesNotExistException() {
    super("El lote no existe.");
  }
}
