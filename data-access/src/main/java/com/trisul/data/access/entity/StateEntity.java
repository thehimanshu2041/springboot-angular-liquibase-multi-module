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
@Table(name = "STATE_ENTITY")
public class StateEntity {

  @Id
  @SequenceGenerator(
      name = "STATE_ENTITY_SEQ",
      sequenceName = "STATE_ENTITY_SEQ",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STATE_ENTITY_SEQ")
  @Column(name = "STATE_ID", insertable = false, updatable = false)
  private Long stateID;

  @Column(name = "STATE_KEY", nullable = false, unique = true)
  private String stateKey;

  @Column(name = "STATE_SHORT_DESCRIPTION", nullable = false)
  private String stateShortDescription;

  @Column(name = "STATE_DESCRIPTION", nullable = false)
  private String stateDescription;

  @Column(name = "STATE_IS_AVAILABLE", nullable = false)
  private Boolean stateIsAvailable;

  @Column(name = "STATE_PRIORITY")
  private Long statePriority;

  @Column(name = "STATE_COUNTRY_ID", nullable = false)
  private Long stateCountryId;

  @Column(name = "STATE_CREATED_BY", nullable = false)
  private String stateCreatedBy;

  @Column(name = "STATE_UPDATED_BY", nullable = false)
  private String stateUpdatedBy;

  @CreationTimestamp
  @Column(name = "STATE_CREATED_DATE_TIME", nullable = false)
  private Date stateCreatedDateTime;

  @UpdateTimestamp
  @Column(name = "STATE_MODIFIED_DATE_TIME", nullable = false)
  private Date stateModifiedDateTime;

  @PrePersist
  private void prePersistFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.stateCreatedBy = userStore.getLoggedInUser();
    this.stateUpdatedBy = userStore.getLoggedInUser();
    this.stateIsAvailable = true;
  }

  @PreUpdate
  public void preUpdateFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.stateUpdatedBy = userStore.getLoggedInUser();
  }
}
