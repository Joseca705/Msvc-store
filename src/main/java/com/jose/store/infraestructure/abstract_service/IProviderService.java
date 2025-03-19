package com.jose.store.infraestructure.abstract_service;

import com.jose.store.api.model.projection.ProviderProjection;
import org.springframework.data.domain.Page;

public interface IProviderService {
  Page<ProviderProjection> getPaginatedAndFilteredProviders(
    String name,
    int page,
    int size
  );
}
