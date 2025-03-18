package com.jose.store.api.controller;

import com.jose.store.api.model.projection.ProductProjection;
import com.jose.store.api.model.request.CreateProductDto;
import com.jose.store.api.model.response.CreatedProduct;
import com.jose.store.api.model.response.ProductResponseDto;
import com.jose.store.infraestructure.abstract_service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

  private final IProductService productService;

  @GetMapping
  public ResponseEntity<
    Page<ProductProjection>
  > getPaginatedAndFilteredProducts(
    @RequestParam(defaultValue = "") String name,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "5") int size
  ) {
    Page<ProductProjection> products =
      this.productService.getPaginatedAndFilteredProducts(name, page, size);
    return ResponseEntity.ok(products);
  }

  @PostMapping("")
  public ResponseEntity<CreatedProduct> createProduct(
    @RequestBody @Valid CreateProductDto productDto
  ) {
    CreatedProduct message = this.productService.create(productDto);
    return new ResponseEntity<CreatedProduct>(message, HttpStatus.CREATED);
  }

  @GetMapping(path = "/code")
  public ResponseEntity<ProductResponseDto> getProductByCode(
    @RequestParam(required = true, value = "code") String code
  ) {
    ProductResponseDto product = this.productService.findProductByCode(code);
    return ResponseEntity.ok(product);
  }
}
