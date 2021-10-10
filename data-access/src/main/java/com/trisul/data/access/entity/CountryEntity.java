package com.trisul.data.access.entity;

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
@Table(name = "COUNTRY_ENTITY")
public class CountryEntity {

  @Id
  @SequenceGenerator(
      name = "COUNTRY_ENTITY_SEQ",
      sequenceName = "COUNTRY_ENTITY_SEQ",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COUNTRY_ENTITY_SEQ")
  @Column(name = "COUNTRY_ID", insertable = false, updatable = false)
  private Long countryID;

  @Column(name = "COUNTRY_KEY", nullable = false, unique = true)
  private String countryKey;

  @Column(name = "COUNTRY_SHORT_DESCRIPTION", nullable = false)
  private String countryShortDescription;

  @Column(name = "COUNTRY_DESCRIPTION", nullable = false)
  private String countryDescription;

  @Column(name = "COUNTRY_IS_AVAILABLE", nullable = false)
  private Boolean countryIsAvailable;

  @Column(name = "COUNTRY_PRIORITY")
  private Long countryPriority;

  @Column(name = "COUNTRY_PHONE_CODE", nullable = false)
  private Long countryPhoneCode;

  @Column(name = "COUNTRY_CREATED_BY", nullable = false)
  private String countryCreatedBy;

  @Column(name = "COUNTRY_UPDATED_BY", nullable = false)
  private String countryUpdatedBy;

  @CreationTimestamp
  @Column(name = "COUNTRY_CREATED_DATE_TIME", nullable = false)
  private Date countryCreatedDateTime;

  @UpdateTimestamp
  @Column(name = "COUNTRY_MODIFIED_DATE_TIME", nullable = false)
  private Date countryModifiedDateTime;

  @PrePersist
  private void prePersistFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.countryCreatedBy = userStore.getLoggedInUser();
    this.countryUpdatedBy = userStore.getLoggedInUser();
    this.countryIsAvailable = true;
  }

  @PreUpdate
  public void preUpdateFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.countryUpdatedBy = userStore.getLoggedInUser();
  }
}
