package com.jose.store.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jose.store.api.model.projection.PriceBatchProjection;
import com.jose.store.api.model.request.CreateBatchStockDto;
import com.jose.store.api.model.request.IdBatchProductDto;
import com.jose.store.api.model.response.CreatedBatchStock;
import com.jose.store.api.model.response.PriceBatchResponse;
import com.jose.store.domain.entity.BatchStock;
import com.jose.store.domain.repository.BatchStockRepository;
import com.jose.store.domain.repository.ProductRepository;
import com.jose.store.domain.repository.ProviderRepository;
import com.jose.store.infraestructure.client.KardexClient;
import com.jose.store.infraestructure.exception.BatchStockDoesNotExistException;
import com.jose.store.infraestructure.exception.ProductDoesNotExistException;
import com.jose.store.infraestructure.exception.ProviderDoesNotExistException;
import com.jose.store.infraestructure.service.BatchStockService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BatchStockServiceTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private ProviderRepository providerRepository;

  @Mock
  private BatchStockRepository batchStockRepository;

  @Mock
  private KardexClient kardexClient;

  @InjectMocks
  private BatchStockService batchStockService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @DisplayName("Este metodo crea exitosamente un lote stock")
  @Test
  void crearLoteStock_Exitoso() {
    CreateBatchStockDto request = new CreateBatchStockDto(
      20,
      11,
      LocalDate.now(),
      30,
      BigDecimal.valueOf(70.5)
    );

    BatchStock savedStock = new BatchStock();
    savedStock.setId(1);
    savedStock.setInitialAmount(30);
    savedStock.setPurchasePrice(BigDecimal.valueOf(70.5));

    when(this.productRepository.existsById(request.getProductId())).thenReturn(
      true
    );
    when(
      this.providerRepository.existsById(request.getProviderId())
    ).thenReturn(true);

    when(this.batchStockRepository.save(any(BatchStock.class))).thenReturn(
      savedStock
    );

    CreatedBatchStock response = this.batchStockService.create(request);

    assertNotNull(response);
    assertEquals("Batch stock created succesfully", response.getMessage());

    verify(this.productRepository).existsById(request.getProductId());
    verify(this.providerRepository).existsById(request.getProviderId());
    verify(this.batchStockRepository).save(any(BatchStock.class));

    verify(this.kardexClient).saveSaleIntoKardex(anyList());
  }

  @DisplayName(
    "Este metodo lanza una excepcion en caso de que el producto no exista."
  )
  @Test
  void crearLoteStock_throwsProductDoesNotExistException() {
    CreateBatchStockDto request = new CreateBatchStockDto(
      39102,
      1,
      LocalDate.now(),
      30,
      BigDecimal.valueOf(70.5)
    );
    when(this.productRepository.existsById(request.getProductId())).thenReturn(
      false
    );

    assertThrows(ProductDoesNotExistException.class, () -> {
      this.batchStockService.create(request);
    });

    verify(this.productRepository).existsById(request.getProductId());
    verify(this.providerRepository, never()).existsById(anyInt());
    verify(this.batchStockRepository, never()).save(any(BatchStock.class));
    verify(this.kardexClient, never()).saveSaleIntoKardex(anyList());
  }

  @DisplayName(
    "Este metodo lanza una excepcion en caso de que el producto no exista."
  )
  @Test
  void crearLoteStock_throwsProviderDoesNotExistException() {
    CreateBatchStockDto request = new CreateBatchStockDto(
      39102,
      1390,
      LocalDate.now(),
      30,
      BigDecimal.valueOf(70.5)
    );

    when(this.productRepository.existsById(request.getProductId())).thenReturn(
      true
    );
    when(this.providerRepository.existsById(request.getProductId())).thenReturn(
      false
    );

    assertThrows(ProviderDoesNotExistException.class, () -> {
      this.batchStockService.create(request);
    });

    verify(this.productRepository).existsById(request.getProductId());
    verify(this.providerRepository).existsById(request.getProviderId());
    verify(this.batchStockRepository, never()).save(any(BatchStock.class));
    verify(this.kardexClient, never()).saveSaleIntoKardex(anyList());
  }

  @DisplayName(
    "Este metodo te devuelve los precios unitarios de compra de los lotes."
  )
  @Test
  void buscarPreciosDeUnLoteIds_Exitoso() {
    List<IdBatchProductDto> request = List.of(
      new IdBatchProductDto(1, 10),
      new IdBatchProductDto(2, 11)
    );

    PriceBatchProjection batch1 = mock(PriceBatchProjection.class);
    when(batch1.getId()).thenReturn(1);
    when(batch1.getProductId()).thenReturn(10);
    when(batch1.getPurchasePrice()).thenReturn(BigDecimal.valueOf(500));

    PriceBatchProjection batch2 = mock(PriceBatchProjection.class);
    when(batch2.getId()).thenReturn(2);
    when(batch2.getProductId()).thenReturn(11);
    when(batch2.getPurchasePrice()).thenReturn(BigDecimal.valueOf(700));

    List<PriceBatchProjection> mockBatches = List.of(batch1, batch2);
    when(batchStockRepository.findByIdIn(anyList())).thenReturn(mockBatches);

    List<PriceBatchResponse> response = batchStockService.FindPriceFromBatch(
      request
    );

    assertNotNull(response);
    assertEquals(1, response.get(0).getId());
    assertEquals(BigDecimal.valueOf(500), response.get(0).getPurchasePrice());
    assertEquals(2, response.get(1).getId());
    assertEquals(BigDecimal.valueOf(700), response.get(1).getPurchasePrice());

    verify(batchStockRepository).findByIdIn(anyList());
  }

  @DisplayName(
    "Este metodo lanza una excepcion donde se indica que un lote no existe."
  )
  @Test
  void buscarPreciosDeUnLoteIds_throwsBatchDoesNotExistException() {
    List<IdBatchProductDto> request = List.of(new IdBatchProductDto(1, 101));
    when(this.batchStockRepository.findByIdIn(anyList())).thenReturn(List.of());

    assertThrows(BatchStockDoesNotExistException.class, () -> {
      this.batchStockService.FindPriceFromBatch(request);
    });

    verify(this.batchStockRepository).findByIdIn(anyList());
  }

  @DisplayName(
    "Este metodo lanza una excepcion cuando el producto no existe en el lote."
  )
  @Test
  void buscarPreciosDeUnLoteIds_throwsProductDoesNotExistException() {
    List<IdBatchProductDto> request = List.of(
      new IdBatchProductDto(1, 10),
      new IdBatchProductDto(2, 99)
    );

    PriceBatchProjection batch1 = mock(PriceBatchProjection.class);
    when(batch1.getId()).thenReturn(1);
    when(batch1.getProductId()).thenReturn(10);
    when(batch1.getPurchasePrice()).thenReturn(BigDecimal.valueOf(500));

    PriceBatchProjection batch2 = mock(PriceBatchProjection.class);
    when(batch2.getId()).thenReturn(2);
    when(batch2.getProductId()).thenReturn(11);
    when(batch2.getPurchasePrice()).thenReturn(BigDecimal.valueOf(700));

    List<PriceBatchProjection> mockBatches = List.of(batch1, batch2);
    when(this.batchStockRepository.findByIdIn(anyList())).thenReturn(
      mockBatches
    );

    assertThrows(ProductDoesNotExistException.class, () ->
      this.batchStockService.FindPriceFromBatch(request)
    );

    verify(this.batchStockRepository).findByIdIn(anyList());
  }
}
