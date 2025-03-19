package com.jose.store.api.controller;

import com.jose.store.api.model.projection.ProviderProjection;
import com.jose.store.infraestructure.abstract_service.IProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/provider")
public class ProviderController {

  private final IProviderService providerService;

  @GetMapping(path = "")
  public ResponseEntity<
    Page<ProviderProjection>
  > getPaginatedAndFilteredProducts(
    @RequestParam(defaultValue = "") String name,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "5") int size
  ) {
    Page<ProviderProjection> providers =
      this.providerService.getPaginatedAndFilteredProviders(name, page, size);
    return ResponseEntity.ok(providers);
  }
}
