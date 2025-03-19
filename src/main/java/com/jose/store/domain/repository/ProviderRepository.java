package com.jose.store.domain.repository;

import com.jose.store.api.model.projection.ProviderProjection;
import com.jose.store.domain.entity.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {
  Page<ProviderProjection> findByNameContainingIgnoreCase(
    Pageable pageable,
    String name
  );
}
