package com.jose.store.api.model.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBatchStockDto implements Serializable {

  @NotNull(message = "El campo productId no debe ser nulo.")
  @Min(value = 1, message = "El valor de productId no debe ser menor a 1.")
  private Integer productId;

  @NotNull(message = "El campo providerId no debe ser nulo.")
  @Min(value = 1, message = "El valor de providerId no debe ser menor a 1.")
  private Integer providerId;

  @NotNull(message = "El campo expirationDate no debe ser nulo.")
  @Future(message = "La fecha debe ser futura.")
  private LocalDate expirationDate;

  @Min(value = 1, message = "El valor de amount no debe ser menor a 1.")
  @NotNull(message = "El campo amount no debe ser nulo.")
  private Integer amount;

  @Digits(
    integer = 38,
    fraction = 4,
    message = "El valor de purchasePrice puede contener hasta 4 decimales."
  )
  @DecimalMin(
    value = "0.10",
    message = "El valor de purchasePrice no debe ser menor a 0.10."
  )
  @NotNull(message = "El campo purchasePrice no debe ser nulo.")
  private BigDecimal purchasePrice;
}
