package com.jose.store.api.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBatchAmountDto {

  @NotNull
  @Min(1)
  private Integer id;

  @NotNull
  @Min(1)
  private Integer amount;
}
