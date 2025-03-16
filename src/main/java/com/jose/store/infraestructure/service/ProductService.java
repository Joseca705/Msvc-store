package com.jose.store.infraestructure.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jose.store.api.model.projection.ProductByCodeProjection;
import com.jose.store.api.model.projection.ProductProjection;
import com.jose.store.api.model.request.CreateProductDto;
import com.jose.store.api.model.response.CreatedProduct;
import com.jose.store.api.model.response.ProductLessThanUmbral;
import com.jose.store.api.model.response.ProductResponseDto;
import com.jose.store.domain.entity.Product;
import com.jose.store.domain.repository.BatchStockRepository;
import com.jose.store.domain.repository.ProductRepository;
import com.jose.store.infraestructure.abstract_service.IProductService;
import com.jose.store.infraestructure.exception.CodeNotValidException;
import com.jose.store.infraestructure.exception.ExistingRecordException;
import com.jose.store.infraestructure.exception.ProductDoesNotExistException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
  private final BatchStockRepository batchStockRepository;
  private final ObjectMapper mapper;

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

    return new CreatedProduct("Produc created succesfully");
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

  @Override
  public ProductResponseDto findProductByCode(String code) {
    String[] codeBatch = code.split("-");
    if (codeBatch.length == 1) throw new CodeNotValidException();

    ProductByCodeProjection productProjection =
      this.productRepository.findByCode(codeBatch[0]).orElseThrow(() ->
          new ProductDoesNotExistException()
        );

    Integer batchId = Integer.parseInt(codeBatch[1]);
    Boolean existsBatch =
      this.batchStockRepository.existsByIdAndProduct(
          batchId,
          new Product(productProjection.getId())
        );

    if (!existsBatch) throw new ProductDoesNotExistException();

    return entityToDto(productProjection, batchId);
  }

  @Override
  public List<ProductLessThanUmbral> findProductsLessThanUmbralStock() {
    List<String> stringProducts =
      this.productRepository.findProductsLessThanUmbralStock();

    return stringProducts
      .stream()
      .map(product -> {
        try {
          return mapper.readValue(product, ProductLessThanUmbral.class);
        } catch (JsonProcessingException e) {
          throw new RuntimeException("Error parseando", e);
        }
      })
      .collect(Collectors.toList());
  }

  private Product dtoToEntity(CreateProductDto dto, String code) {
    Product product = new Product();
    BeanUtils.copyProperties(dto, product);
    product.setCode(code);
    return product;
  }

  private ProductResponseDto entityToDto(
    ProductByCodeProjection entity,
    Integer batchId
  ) {
    return new ProductResponseDto(
      entity.getId(),
      entity.getName(),
      entity.getSalePrice(),
      batchId
    );
  }
}
