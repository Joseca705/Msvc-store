package com.jose.store.api.controller;

import com.jose.store.api.model.projection.ProductProjection;
import com.jose.store.api.model.request.CreateProductDto;
import com.jose.store.api.model.response.CreatedProduct;
import com.jose.store.api.model.response.ProductResponseDto;
import com.jose.store.infraestructure.abstract_service.IProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
@Validated
public class ProductController {

  private final IProductService productService;

  @GetMapping(path = "")
  public ResponseEntity<
    Page<ProductProjection>
  > getPaginatedAndFilteredProducts(
    @RequestParam(defaultValue = "", required = false) String name,
    @RequestParam(
      defaultValue = "0",
      required = false
    ) @PositiveOrZero int page,
    @RequestParam(defaultValue = "5", required = false) @Positive int size
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
    @RequestParam(required = true, name = "code") String code
  ) {
    ProductResponseDto product = this.productService.findProductByCode(code);
    return ResponseEntity.ok(product);
  }

  @PatchMapping(path = "/{id}")
  public ResponseEntity<CreatedProduct> updateProduct(
    @PathVariable(name = "id") @Positive Integer id,
    @RequestBody CreateProductDto dto
  ) {
    CreatedProduct response = this.productService.update(dto, id);
    return ResponseEntity.ok(response);
  }
}
