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
@Table(name = "CITY_ENTITY")
public class CityEntity {

  @Id
  @SequenceGenerator(name = "CITY_ENTITY_SEQ", sequenceName = "CITY_ENTITY_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CITY_ENTITY_SEQ")
  @Column(name = "CITY_ID", insertable = false, updatable = false)
  private Long cityID;

  @Column(name = "CITY_KEY", nullable = false, unique = true)
  private String cityKey;

  @Column(name = "CITY_SHORT_DESCRIPTION", nullable = false)
  private String cityShortDescription;

  @Column(name = "CITY_DESCRIPTION", nullable = false)
  private String cityDescription;

  @Column(name = "CITY_IS_AVAILABLE", nullable = false)
  private Boolean cityIsAvailable;

  @Column(name = "CITY_PRIORITY")
  private Long cityPriority;

  @Column(name = "CITY_STATE_ID", nullable = false)
  private Long cityStateId;

  @Column(name = "CITY_CREATED_BY", nullable = false)
  private String cityCreatedBy;

  @Column(name = "CITY_UPDATED_BY", nullable = false)
  private String cityUpdatedBy;

  @CreationTimestamp
  @Column(name = "CITY_CREATED_DATE_TIME", nullable = false)
  private Date cityCreatedDateTime;

  @UpdateTimestamp
  @Column(name = "CITY_MODIFIED_DATE_TIME", nullable = false)
  private Date cityModifiedDateTime;

  @PrePersist
  private void prePersistFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.cityCreatedBy = userStore.getLoggedInUser();
    this.cityUpdatedBy = userStore.getLoggedInUser();
    this.cityIsAvailable = true;
  }

  @PreUpdate
  public void preUpdateFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.cityUpdatedBy = userStore.getLoggedInUser();
  }
}
