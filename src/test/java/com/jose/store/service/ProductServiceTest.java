package com.jose.store.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jose.store.api.model.projection.ProductByCodeProjection;
import com.jose.store.api.model.request.CreateProductDto;
import com.jose.store.api.model.response.CreatedProduct;
import com.jose.store.api.model.response.ProductResponseDto;
import com.jose.store.domain.entity.Product;
import com.jose.store.domain.repository.ProductRepository;
import com.jose.store.infraestructure.exception.ExistingRecordException;
import com.jose.store.infraestructure.exception.ProductDoesNotExistException;
import com.jose.store.infraestructure.service.ProductService;
import java.math.BigDecimal;
import java.util.Optional;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductService productService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @DisplayName("Este metodo indica la creacion exitosa de un producto.")
  @Test
  void crearProduct_Exitoso() {
    // Creamos objetos de prueba
    CreateProductDto request = new CreateProductDto(
      "Carne de Pollo",
      BigDecimal.valueOf(38.90),
      50
    );

    String formattedName = "CARNE DE POLLO";
    String code = DigestUtils.sha256Hex(formattedName);

    when(this.productRepository.existsByCode(code)).thenReturn(false);

    // Iniciamos la prueba
    CreatedProduct response = this.productService.create(request);

    assertNotNull(response);
    assertEquals("Producto creado exitosamente.", response.getMessage());

    // Verificaciones
    verify(this.productRepository).existsByCode(code);
    verify(this.productRepository).save(any(Product.class));
  }

  @DisplayName(
    "Este metodo se encarga de lanzar una excepcion cuando ya exista el codigo de un producto."
  )
  @Test
  void crearProducto_ThrowsExistingRecordException() {
    // Creamos objetos de prueba
    CreateProductDto request = new CreateProductDto(
      "Carne de Pollo",
      BigDecimal.valueOf(38.90),
      50
    );

    String formattedName = "CARNE DE POLLO";
    String code = DigestUtils.sha256Hex(formattedName);

    when(this.productRepository.existsByCode(code)).thenReturn(true);

    // Assert
    Exception exception = assertThrows(ExistingRecordException.class, () ->
      this.productService.create(request)
    );

    assertEquals("Product already exists", exception.getMessage());

    // Verificar que el metodo para guardar no haya sido llamado
    verify(this.productRepository, never()).save(any(Product.class));
  }

  @DisplayName(
    "Este metodo devuelve un mensaje de que el producto ha sido guardado exitosamente."
  )
  @Test
  void actualizarProducto_Exitoso() {
    // Creamos objetos de prueba
    Integer productId = 1;
    Product existingProduct = new Product();
    existingProduct.setId(productId);
    existingProduct.setName("CARNE DE CERDO");
    existingProduct.setUmbralStock(30);
    existingProduct.setSalePrice(BigDecimal.valueOf(50));

    when(this.productRepository.findById(productId)).thenReturn(
      Optional.of(existingProduct)
    );

    CreateProductDto request = new CreateProductDto(
      "Carne de Cerdo SOFIA",
      BigDecimal.valueOf(35),
      10
    );

    String formattedName = "CARNE DE CERDO SOFIA";
    String newCode = DigestUtils.sha256Hex(formattedName);

    CreatedProduct response = this.productService.update(request, productId);

    // Assert
    assertNotNull(response);
    assertEquals("Producto actualizado exitosamente.", response.getMessage());

    assertEquals(formattedName, existingProduct.getName());
    assertEquals(10, existingProduct.getUmbralStock());
    assertEquals(BigDecimal.valueOf(35), existingProduct.getSalePrice());
    assertEquals(newCode, existingProduct.getCode());

    // Verificaciones
    verify(this.productRepository).findById(productId);
    verify(this.productRepository).save(existingProduct);
  }

  @DisplayName(
    "Este metodo devuelve una excepcion en caso de que el producto no exista."
  )
  @Test
  void actualizarProducto_throwsProductDoesNotExistException() {
    Integer productId = 1;
    when(this.productRepository.findById(productId)).thenReturn(
      Optional.empty()
    );

    CreateProductDto request = new CreateProductDto(
      "CARNE DE RES",
      BigDecimal.valueOf(80),
      50
    );

    assertThrows(ProductDoesNotExistException.class, () ->
      this.productService.update(request, productId)
    );

    // Verificaciones
    verify(this.productRepository).findById(productId);
    verify(this.productRepository, never()).save(any(Product.class));
  }

  @DisplayName(
    "Este metodo te devuelve informacion acerca de un producto por un codigo."
  )
  @Test
  void buscarProductoPorCodigo_Exitoso() {
    // Creamos los objetos de prueba
    String code = "dmasiodmu3213";

    ProductByCodeProjection mockProjection = mock(
      ProductByCodeProjection.class
    );
    when(mockProjection.getId()).thenReturn(1);
    when(mockProjection.getName()).thenReturn("CARNE DE POLLO");
    when(mockProjection.getSalePrice()).thenReturn(BigDecimal.valueOf(38.90));

    when(this.productRepository.findByCode(code)).thenReturn(
      Optional.of(mockProjection)
    );

    ProductResponseDto response = this.productService.findProductByCode(code);

    assertNotNull(response);
    assertEquals("CARNE DE POLLO", response.getName());
    assertEquals(1, response.getId());
    assertEquals(BigDecimal.valueOf(38.90), response.getSalePrice());

    // Verificacion
    verify(this.productRepository).findByCode(code);
  }

  @DisplayName(
    "Este metodo devuelve un error de producto no encontrado en caso de que el codigo sea incorrecto."
  )
  @Test
  void buscarProductoPorCodigo_ThrowsProductDoesNotExistException() {
    // Creamos los objetos de prueba
    String code = "dmasiodmu3213";

    when(this.productRepository.findByCode(code)).thenReturn(Optional.empty());

    assertThrows(ProductDoesNotExistException.class, () ->
      this.productService.findProductByCode(code)
    );

    // Verificacion
    verify(this.productRepository).findByCode(code);
  }
}
