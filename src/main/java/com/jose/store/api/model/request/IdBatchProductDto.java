package com.jose.store.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdBatchProductDto {

  private Integer batchId;

  private Integer productId;
}
