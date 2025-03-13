package com.jose.store.api.model.request;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto implements Serializable {

  private String name;

  private BigDecimal salePrice;

  private Integer umbralStock;
}
