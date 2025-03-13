package com.jose.store.domain.repository;

import com.jose.store.domain.entity.BatchStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchStockRepository
  extends JpaRepository<BatchStock, Integer> {}
