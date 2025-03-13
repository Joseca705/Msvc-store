package com.jose.store.domain.repository;

import com.jose.store.api.model.projection.ProductProjection;
import com.jose.store.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
  Page<ProductProjection> findByNameContainingIgnoreCase(
    Pageable pageable,
    String name
  );
}
