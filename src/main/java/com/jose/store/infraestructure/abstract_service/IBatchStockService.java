package com.jose.store.infraestructure.abstract_service;

import com.jose.store.api.model.projection.PriceBatchProjection;
import com.jose.store.api.model.request.CreateBatchStockDto;
import com.jose.store.api.model.response.CreatedBatchStock;
import java.util.List;

public interface IBatchStockService
  extends CrudService<CreateBatchStockDto, CreatedBatchStock, Integer> {
  List<PriceBatchProjection> FindPriceFromBatch(List<Integer> ids);
}
