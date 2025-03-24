package com.jose.store.api.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdBatchProductDto {

  @NotNull(message = "El campo batchId no puede ser null.")
  @Min(value = 1, message = "El valor minimo de batchId es 1.")
  private Integer batchId;

  @NotNull(message = "El campo productId no puede ser null.")
  @Min(value = 1, message = "El valor minimo de productId es 1.")
  private Integer productId;
}
