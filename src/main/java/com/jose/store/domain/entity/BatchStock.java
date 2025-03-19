package com.jose.store.domain.entity;

import com.jose.store.domain.constant.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "batch_stock")
public class BatchStock extends BaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "purchase_price", nullable = false, precision = 38, scale = 2)
  private BigDecimal purchasePrice;

  @Column(name = "initial_amount", nullable = false)
  private Integer initialAmount;

  @Column(name = "expiration_date", nullable = false)
  private LocalDate expirationDate;

  @Column(name = "acquisition_date", nullable = false)
  private LocalDate acquisitionDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
    name = "product_id",
    referencedColumnName = "id",
    nullable = false
  )
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
    name = "provider_id",
    referencedColumnName = "id",
    nullable = false
  )
  private Provider provider;

  @PrePersist
  public void onPrePersist() {
    this.setStatus(Status.ACTIVE);
    this.acquisitionDate = LocalDate.now();
  }
}
