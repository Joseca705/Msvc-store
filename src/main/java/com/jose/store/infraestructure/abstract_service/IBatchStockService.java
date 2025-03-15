package com.jose.store.infraestructure.abstract_service;

import com.jose.store.api.model.request.CreateBatchStockDto;
import com.jose.store.api.model.response.CreatedBatchStock;

public interface IBatchStockService
  extends CrudService<CreateBatchStockDto, CreatedBatchStock, Integer> {}
