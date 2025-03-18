package com.jose.store.infraestructure.abstract_service;

import com.jose.store.api.model.projection.ProductProjection;
import com.jose.store.api.model.request.CreateProductDto;
import com.jose.store.api.model.response.CreatedProduct;
import com.jose.store.api.model.response.ProductResponseDto;
import org.springframework.data.domain.Page;

public interface IProductService
  extends CrudService<CreateProductDto, CreatedProduct, Integer> {
  Page<ProductProjection> getPaginatedAndFilteredProducts(
    String name,
    int page,
    int size
  );

  ProductResponseDto findProductByCode(String code);
}
