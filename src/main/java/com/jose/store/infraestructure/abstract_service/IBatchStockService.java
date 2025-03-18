package com.jose.store.infraestructure.abstract_service;

import com.jose.store.api.model.projection.BatchStockSimpleInfoProjection;
import com.jose.store.api.model.request.CreateBatchStockDto;
import com.jose.store.api.model.response.CreatedBatchStock;
import java.util.List;

public interface IBatchStockService
  extends CrudService<CreateBatchStockDto, CreatedBatchStock, Integer> {
  List<BatchStockSimpleInfoProjection> getBatchStocksSimpleInfo(
    List<Integer> ids
  );
}
