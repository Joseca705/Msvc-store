package com.jose.store.infraestructure.service;

import com.jose.store.api.model.projection.ProviderProjection;
import com.jose.store.domain.repository.ProviderRepository;
import com.jose.store.infraestructure.abstract_service.IProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProviderService implements IProviderService {

  private final ProviderRepository providerRepository;

  @Override
  public Page<ProviderProjection> getPaginatedAndFilteredProviders(
    String name,
    int page,
    int size
  ) {
    Pageable pageable = PageRequest.of(page, size);
    return this.providerRepository.findByNameContainingIgnoreCase(
        pageable,
        name
      );
  }
}
