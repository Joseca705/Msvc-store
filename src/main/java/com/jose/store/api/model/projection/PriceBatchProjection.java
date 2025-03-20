package com.jose.store.api.model.projection;

import java.math.BigDecimal;

public interface PriceBatchProjection {
  Integer getId();

  Integer getProductId();

  BigDecimal getPurchasePrice();
}
