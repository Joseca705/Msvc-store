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
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "providers")
public class Provider extends BaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(length = 100, nullable = false)
  private String direction;

  @Column(length = 20, nullable = false, unique = true)
  private String phone;

  @Column(length = 100, nullable = false, unique = true)
  private String email;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "provider", fetch = FetchType.LAZY)
  private List<BatchStock> batchs;

  public Provider(Integer id) {
    this.id = id;
  }

  @PrePersist
  public void onPrePersist() {
    this.setStatus(Status.ACTIVE);
  }
}
