package com.jose.store.infraestructure.abstract_service;

import com.jose.store.api.model.projection.BatchStockSimpleInfoProjection;
import com.jose.store.api.model.request.CreateBatchStockDto;
import com.jose.store.api.model.response.CreatedBatchStock;
import com.jose.store.api.model.response.UpdatedAmountBatchResponse;
import java.util.List;

public interface IBatchStockService
  extends CrudService<CreateBatchStockDto, CreatedBatchStock, Integer> {
  UpdatedAmountBatchResponse updateAmountById(Integer id, Integer amount);

  List<BatchStockSimpleInfoProjection> getBatchStocksSimpleInfo(
    List<Integer> ids
  );
}
