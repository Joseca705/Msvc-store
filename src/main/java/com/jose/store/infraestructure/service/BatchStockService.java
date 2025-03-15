package com.jose.store.infraestructure.service;

import com.jose.store.api.model.request.CreateBatchStockDto;
import com.jose.store.api.model.response.CreatedBatchStock;
import com.jose.store.api.model.response.UpdatedAmountBatchResponse;
import com.jose.store.domain.entity.BatchStock;
import com.jose.store.domain.entity.Product;
import com.jose.store.domain.entity.Provider;
import com.jose.store.domain.repository.BatchStockRepository;
import com.jose.store.domain.repository.ProductRepository;
import com.jose.store.domain.repository.ProviderRepository;
import com.jose.store.infraestructure.abstract_service.IBatchStockService;
import com.jose.store.infraestructure.exception.BatchStockDoesNotExistException;
import com.jose.store.infraestructure.exception.ProductDoesNotExistException;
import com.jose.store.infraestructure.exception.ProviderDoesNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BatchStockService implements IBatchStockService {

  private final ProductRepository productRepository;
  private final ProviderRepository providerRepository;
  private final BatchStockRepository batchStockRepository;

  @Transactional
  @Override
  public CreatedBatchStock create(CreateBatchStockDto request) {
    Boolean existsProduct =
      this.productRepository.existsById(request.getProductId());
    if (!existsProduct) throw new ProductDoesNotExistException();

    Boolean existsProvider =
      this.providerRepository.existsById(request.getProviderId());
    if (!existsProvider) throw new ProviderDoesNotExistException();

    this.batchStockRepository.save(dtoToEntity(request));

    return new CreatedBatchStock("Batch stock created succesfully");
  }

  @Override
  @Transactional
  public UpdatedAmountBatchResponse updateAmountById(
    Integer id,
    Integer amount
  ) {
    BatchStock batch =
      this.batchStockRepository.findById(id).orElseThrow(() ->
          new BatchStockDoesNotExistException()
        );

    Integer currentAmount = batch.getCurrentAmount() - amount;
    batch.setCurrentAmount(currentAmount);
    this.batchStockRepository.save(batch);

    return new UpdatedAmountBatchResponse("Batch updated succesfully.");
  }

  @Override
  public CreatedBatchStock read(Integer id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'read'");
  }

  @Override
  public CreatedBatchStock update(CreateBatchStockDto request, Integer id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void delete(Integer id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  private BatchStock dtoToEntity(CreateBatchStockDto dto) {
    BatchStock batch = new BatchStock();
    BeanUtils.copyProperties(dto, batch);
    batch.setProvider(new Provider(dto.getProviderId()));
    batch.setProduct(new Product(dto.getProductId()));
    batch.setInitialAmount(dto.getAmount());
    return batch;
  }
}
