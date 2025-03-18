package com.jose.store.domain.repository;

import com.jose.store.api.model.projection.ProductByCodeProjection;
import com.jose.store.api.model.projection.ProductProjection;
import com.jose.store.domain.entity.Product;
import java.util.Optional;
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

  Boolean existsByCode(String code);

  Optional<ProductByCodeProjection> findByCode(String code);
}
