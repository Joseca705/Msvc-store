package com.jose.store.api.model.projection;

import java.math.BigDecimal;

public interface BatchStockSimpleInfoProjection {
  Integer getId();

  Integer getInitialAmount();

  BigDecimal getPurchasePrice();

  Integer getProductId();
}
