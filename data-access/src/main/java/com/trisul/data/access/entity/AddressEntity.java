package com.trisul.data.access.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trisul.core.security.user.UserStoreImpl;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ADDRESS_ENTITY")
public class AddressEntity {

  @Id
  @SequenceGenerator(
      name = "ADDRESS_ENTITY_SEQ",
      sequenceName = "ADDRESS_ENTITY_SEQ",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_ENTITY_SEQ")
  @Column(name = "ADDRESS_ID", insertable = false, updatable = false)
  private Long addressID;

  @Column(name = "ADDRESS_ADDRESS1", nullable = false)
  private String addressAddress1;

  @Column(name = "ADDRESS_ADDRESS2")
  private String addressAddress2;

  @Column(name = "ADDRESS_ADDRESS3")
  private String addressAddress3;

  @Column(name = "ADDRESS_CITY", nullable = false)
  private Long addressCity;

  @Column(name = "ADDRESS_STATE", nullable = false)
  private Long addressState;

  @Column(name = "ADDRESS_COUNTRY", nullable = false)
  private Long addressCountry;

  @Column(name = "ADDRESS_CREATE_BY", nullable = false)
  private String addressCreateBy;

  @Column(name = "ADDRESS_UPDATED_BY", nullable = false)
  private String addressUpdatedBy;

  @CreationTimestamp
  @Column(name = "ADDRESS_CREATED_DATE_TIME", nullable = false)
  private Date addressCreatedDateTime;

  @UpdateTimestamp
  @Column(name = "ADDRESS_MODIFIED_DATE_TIME", nullable = false)
  private Date addressModifiedDateTime;

  @JsonIgnore
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ADDRESS_USER_ID")
  private UserEntity userEntity;

  @PrePersist
  private void prePersistFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.addressCreateBy = userStore.getLoggedInUser();
    this.addressUpdatedBy = userStore.getLoggedInUser();
  }

  @PreUpdate
  public void preUpdateFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.addressUpdatedBy = userStore.getLoggedInUser();
  }
}
