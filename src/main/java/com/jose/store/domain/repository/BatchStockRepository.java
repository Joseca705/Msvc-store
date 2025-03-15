package com.jose.store.domain.repository;

import com.jose.store.domain.entity.BatchStock;
import com.jose.store.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchStockRepository
  extends JpaRepository<BatchStock, Integer> {
  Boolean existsByIdAndProduct(Integer id, Product product);
}
