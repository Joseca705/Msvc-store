package com.jose.store.infraestructure.abstract_service;

import com.jose.store.api.model.request.CreateBatchStockDto;
import com.jose.store.api.model.request.IdBatchProductDto;
import com.jose.store.api.model.response.CreatedBatchStock;
import com.jose.store.api.model.response.PriceBatchResponse;
import java.util.List;

public interface IBatchStockService
  extends CrudService<CreateBatchStockDto, CreatedBatchStock, Integer> {
  List<PriceBatchResponse> FindPriceFromBatch(List<IdBatchProductDto> ids);
}
