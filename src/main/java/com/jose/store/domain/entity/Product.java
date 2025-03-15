package com.jose.store.domain.entity;

import com.jose.store.domain.constant.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "products")
public class Product extends BaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 100, nullable = false)
  private String code;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(name = "sale_price", nullable = false, precision = 38, scale = 4)
  private BigDecimal salePrice;

  @Column(name = "umbral_stock", nullable = false)
  private Integer umbralStock;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
  private List<BatchStock> batchs;

  public Product(Integer id) {
    this.id = id;
  }

  @PrePersist
  public void onPrePersist() {
    this.setStatus(Status.ACTIVE);
  }
}
