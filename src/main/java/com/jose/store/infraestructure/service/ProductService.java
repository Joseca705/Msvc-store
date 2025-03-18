package com.jose.store.infraestructure.service;

import com.jose.store.api.model.projection.ProductByCodeProjection;
import com.jose.store.api.model.projection.ProductProjection;
import com.jose.store.api.model.request.CreateProductDto;
import com.jose.store.api.model.response.CreatedProduct;
import com.jose.store.api.model.response.ProductResponseDto;
import com.jose.store.domain.entity.Product;
import com.jose.store.domain.repository.ProductRepository;
import com.jose.store.infraestructure.abstract_service.IProductService;
import com.jose.store.infraestructure.exception.ExistingRecordException;
import com.jose.store.infraestructure.exception.ProductDoesNotExistException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

  private final ProductRepository productRepository;

  @Transactional
  @Override
  public CreatedProduct create(CreateProductDto request) {
    String formattedName = request.getName().trim().toUpperCase();
    String code = DigestUtils.sha256Hex(formattedName);
    Boolean existingProduct = this.productRepository.existsByCode(code);

    if (existingProduct) throw new ExistingRecordException(
      "Product already exists"
    );
    request.setName(formattedName);

    this.productRepository.save(this.dtoToEntity(request, code));

    return new CreatedProduct("Producto creado exitosamente.");
  }

  @Override
  public CreatedProduct read(Integer id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'read'");
  }

  @Override
  @Transactional
  public CreatedProduct update(CreateProductDto request, Integer id) {
    Product foundedProduct =
      this.productRepository.findById(id).orElseThrow(() ->
          new ProductDoesNotExistException()
        );

    String formattedName = request.getName().trim().toUpperCase();
    String code = DigestUtils.sha256Hex(formattedName);
    Boolean existingProduct = this.productRepository.existsByCode(code);

    if (existingProduct) throw new ExistingRecordException(
      "Product already exists"
    );

    foundedProduct.setName(formattedName);
    foundedProduct.setUmbralStock(request.getUmbralStock());
    foundedProduct.setSalePrice(request.getSalePrice());
    foundedProduct.setCode(code);

    this.productRepository.save(foundedProduct);

    return new CreatedProduct("Producto actualizado exitosamente.");
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

  @Override
  public ProductResponseDto findProductByCode(String code) {
    ProductByCodeProjection productProjection =
      this.productRepository.findByCode(code).orElseThrow(() ->
          new ProductDoesNotExistException()
        );

    return entityToDto(productProjection);
  }

  private Product dtoToEntity(CreateProductDto dto, String code) {
    Product product = new Product();
    BeanUtils.copyProperties(dto, product);
    product.setCode(code);
    return product;
  }

  private ProductResponseDto entityToDto(ProductByCodeProjection entity) {
    return new ProductResponseDto(
      entity.getId(),
      entity.getName(),
      entity.getSalePrice()
    );
  }
}
