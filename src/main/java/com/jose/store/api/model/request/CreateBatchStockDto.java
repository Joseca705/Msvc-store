package com.jose.store.api.model.request;

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

  @NotNull
  @Min(1)
  private Integer productId;

  @NotNull
  @Min(1)
  private Integer providerId;

  @NotNull
  @Future(message = "Date gotta be future")
  private LocalDate expirationDate;

  @Min(1)
  @NotNull
  private Integer amount;

  @Digits(integer = 38, fraction = 4)
  @NotNull
  private BigDecimal purchasePrice;
}
