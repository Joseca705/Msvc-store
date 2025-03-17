package com.jose.store.api.model.projection;

import java.math.BigDecimal;

public interface BatchStockSimpleInfoProjection {
  Integer getId();

  Integer getInitialAmount();

  Integer getCurrentAmount();

  BigDecimal getPurchasePrice();
}
