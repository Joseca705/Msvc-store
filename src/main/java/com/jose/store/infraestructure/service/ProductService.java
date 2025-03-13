package com.jose.store.infraestructure.service;

import com.jose.store.api.model.projection.ProductProjection;
import com.jose.store.api.model.request.CreateProductDto;
import com.jose.store.api.model.response.CreatedProduct;
import com.jose.store.domain.repository.ProductRepository;
import com.jose.store.infraestructure.abstract_service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

  private final ProductRepository productRepository;

  @Override
  public CreatedProduct create(CreateProductDto request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public CreatedProduct read(Integer id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'read'");
  }

  @Override
  public CreatedProduct update(CreateProductDto request, Integer id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void delete(Integer id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public Page<ProductProjection> getPaginatedAndFilteredProducts(
    String name,
    int page,
    int size
  ) {
    Pageable pageable = PageRequest.of(page, size);
    return this.productRepository.findByNameContainingIgnoreCase(
        pageable,
        name
      );
  }
}
