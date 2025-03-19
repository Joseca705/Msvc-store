package com.jose.store.api.model.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto implements Serializable {

  @NotNull(message = "El campo name no debe ser nulo.")
  @Size(
    min = 3,
    max = 100,
    message = "El tamaño del name debe tener entre 3 y 100 caracteres."
  )
  private String name;

  @NotNull(message = "El campo salePrice no debe ser nulo.")
  @Digits(
    integer = 34,
    fraction = 4,
    message = "El numero de salePrice puede contener hasta 4 decimales."
  )
  @Min(value = 1, message = "El valor de salePrice no debe ser menor a 1.")
  private BigDecimal salePrice;

  @NotNull(message = "El campo umbralStock no debe ser nulo.")
  @Min(value = 1, message = "El valor de umbralStock no debe ser menor a 1.")
  private Integer umbralStock;
}
