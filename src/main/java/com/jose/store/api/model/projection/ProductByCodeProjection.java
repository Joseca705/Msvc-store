package com.jose.store.api.model.projection;

import java.math.BigDecimal;

public interface ProductByCodeProjection {
  Integer getId();

  String getName();

  BigDecimal getSalePrice();
}
