package com.jose.store.domain.repository;

import com.jose.store.api.model.projection.ProductByCodeProjection;
import com.jose.store.api.model.projection.ProductProjection;
import com.jose.store.domain.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
  Page<ProductProjection> findByNameContainingIgnoreCase(
    Pageable pageable,
    String name
  );

  Boolean existsByCode(String code);

  Optional<ProductByCodeProjection> findByCode(String code);

  @Query(
    value = """
    SELECT
    	json_build_object(
    	'id', p.id,
    	'name', p.name,
    	'batchs',
    	coalesce(json_agg(json_build_object(
    'batchId', bs.id,
    	'currentAmount', bs.current_amount
    )))
    ):: TEXT
    FROM
    	products p
    LEFT JOIN batch_stock bs on
    	p.id = bs.product_id
    WHERE
    	bs.current_amount < p.umbral_stock
    	AND bs.active = true
    	AND bs.status = 'ACTIVE'
    GROUP BY p.id;
      """,
    nativeQuery = true
  )
  List<String> findProductsLessThanUmbralStock();
}
