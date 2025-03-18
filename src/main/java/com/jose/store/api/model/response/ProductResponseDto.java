package com.jose.store.api.model.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

  private Integer id;

  private String name;

  private BigDecimal salePrice;
}
