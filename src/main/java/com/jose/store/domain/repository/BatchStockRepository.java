package com.jose.store.domain.repository;

import com.jose.store.api.model.projection.PriceBatchProjection;
import com.jose.store.domain.entity.BatchStock;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchStockRepository
  extends JpaRepository<BatchStock, Integer> {
  List<PriceBatchProjection> findByIdIn(List<Integer> ids);
}
