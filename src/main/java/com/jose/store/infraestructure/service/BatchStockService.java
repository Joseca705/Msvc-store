package com.jose.store.infraestructure.service;

import com.jose.store.api.model.projection.PriceBatchProjection;
import com.jose.store.api.model.request.CreateBatchStockDto;
import com.jose.store.api.model.request.CreateKardexRequest;
import com.jose.store.api.model.request.IdBatchProductDto;
import com.jose.store.api.model.response.CreatedBatchStock;
import com.jose.store.api.model.response.PriceBatchResponse;
import com.jose.store.domain.entity.BatchStock;
import com.jose.store.domain.entity.Product;
import com.jose.store.domain.entity.Provider;
import com.jose.store.domain.repository.BatchStockRepository;
import com.jose.store.domain.repository.ProductRepository;
import com.jose.store.domain.repository.ProviderRepository;
import com.jose.store.infraestructure.abstract_service.IBatchStockService;
import com.jose.store.infraestructure.client.KardexClient;
import com.jose.store.infraestructure.exception.BatchStockDoesNotExistException;
import com.jose.store.infraestructure.exception.ProductDoesNotExistException;
import com.jose.store.infraestructure.exception.ProviderDoesNotExistException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchStockService implements IBatchStockService {

  private final ProductRepository productRepository;
  private final ProviderRepository providerRepository;
  private final BatchStockRepository batchStockRepository;
  private final KardexClient client;

  @Override
  public CreatedBatchStock create(CreateBatchStockDto request) {
    Boolean existsProduct =
      this.productRepository.existsById(request.getProductId());
    if (!existsProduct) throw new ProductDoesNotExistException();

    Boolean existsProvider =
      this.providerRepository.existsById(request.getProviderId());
    if (!existsProvider) throw new ProviderDoesNotExistException();

    BatchStock stock = this.batchStockRepository.save(dtoToEntity(request));

    List<CreateKardexRequest> requests = List.of(
      new CreateKardexRequest(
        "SALIDA",
        stock.getInitialAmount(),
        stock.getInitialAmount(),
        stock.getPurchasePrice(),
        "COMPRA DE",
        stock.getId(),
        request.getProductId(),
        stock.getId()
      )
    );

    this.client.saveSaleIntoKardex(requests);

    return new CreatedBatchStock("Batch stock created succesfully");
  }

  @Override
  public List<PriceBatchResponse> FindPriceFromBatch(
    List<IdBatchProductDto> ids
  ) {
    List<Integer> batchIds = ids
      .stream()
      .map(IdBatchProductDto::getBatchId)
      .toList();
    List<PriceBatchProjection> batchs =
      this.batchStockRepository.findByIdIn(batchIds);

    if (batchs.isEmpty()) throw new BatchStockDoesNotExistException();

    Set<String> validPairs = batchs
      .stream()
      .map(batch -> batch.getId() + "-" + batch.getProductId())
      .collect(Collectors.toSet());

    boolean allMatch = ids
      .stream()
      .allMatch(bp ->
        validPairs.contains(bp.getBatchId() + "-" + bp.getProductId())
      );
    if (!allMatch) throw new ProductDoesNotExistException();

    return batchs
      .stream()
      .map(batch ->
        new PriceBatchResponse(batch.getId(), batch.getPurchasePrice())
      )
      .toList();
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
