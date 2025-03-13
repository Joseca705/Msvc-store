package com.jose.store.api.controller;

import com.jose.store.api.model.projection.ProductProjection;
import com.jose.store.infraestructure.abstract_service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController(value = "/product")
public class ProductController {

  private final IProductService productService;

  @GetMapping(path = "/")
  public ResponseEntity<
    Page<ProductProjection>
  > getPaginatedAndFilteredProducts(
    @RequestParam String name,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "5") int size
  ) {
    Page<ProductProjection> products =
      this.productService.getPaginatedAndFilteredProducts(name, page, size);
    return ResponseEntity.ok(products);
  }
}
