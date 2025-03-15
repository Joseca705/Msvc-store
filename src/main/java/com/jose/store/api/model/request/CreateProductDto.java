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

  @NotNull
  @Size(min = 3, max = 100)
  private String name;

  @Digits(integer = 34, fraction = 4)
  private BigDecimal salePrice;

  @NotNull
  @Min(1)
  private Integer umbralStock;
}
