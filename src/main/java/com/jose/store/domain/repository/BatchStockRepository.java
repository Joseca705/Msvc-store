package com.jose.store.domain.repository;

import com.jose.store.api.model.projection.BatchStockSimpleInfoProjection;
import com.jose.store.domain.entity.BatchStock;
import com.jose.store.domain.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchStockRepository
  extends JpaRepository<BatchStock, Integer> {
  Boolean existsByIdAndProduct(Integer id, Product product);

  List<BatchStockSimpleInfoProjection> findByIdIn(List<Integer> ids);
}
