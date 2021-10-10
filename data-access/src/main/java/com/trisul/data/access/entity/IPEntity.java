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
@Table(name = "IP_ENTITY")
public class IPEntity {

  @Id
  @SequenceGenerator(name = "IP_ENTITY_SEQ", sequenceName = "IP_ENTITY_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IP_ENTITY_SEQ")
  @Column(name = "IP_ID", insertable = false, updatable = false)
  private Long ipID;

  @Column(name = "IP_TOKEN")
  private String ipToken;

  @Column(name = "IP_USERNAME")
  private String ipUsername;

  @Column(name = "IP_PORT")
  private String ipPort;

  @Column(name = "IP_CONTEXT_PATH")
  private String ipContextPath;

  @Column(name = "IP_REQUEST_PATH")
  private String ipRequestPath;

  @Column(name = "IP_ADDRESS")
  private String ipAddress;

  @Column(name = "IP_METHOD")
  private String ipMethod;

  @Column(name = "IP_POSTAL_CODE")
  private String ipPostalCode;

  @Column(name = "IP_LATITUDE")
  private String ipLatitude;

  @Column(name = "IP_LONGITUDE")
  private String ipLongitude;

  @Column(name = "IP_CITY")
  private String ipCity;

  @Column(name = "IP_STATE")
  private String ipState;

  @Column(name = "IP_COUNTRY")
  private String ipCountry;

  @Column(name = "IP_METRO_CODE")
  private String ipMetroCode;

  @Column(name = "IP_AREA_CODE")
  private String ipAreaCode;

  @Column(name = "IP_NUM")
  private String ipNum;

  @Column(name = "IP_URL")
  private String ipURL;

  @Column(name = "IP_REFERER")
  private String ipReferer;

  @Column(name = "IP_CREATED_BY", nullable = false)
  private String ipCreatedBy;

  @Column(name = "IP_UPDATED_BY", nullable = false)
  private String ipUpdatedBy;

  @CreationTimestamp
  @Column(name = "IP_CREATED_DATE_TIME", nullable = false)
  private Date ipCreatedDateTime;

  @UpdateTimestamp
  @Column(name = "IP_MODIFIED_DATE_TIME", nullable = false)
  private Date ipModifiedDateTime;

  @PrePersist
  private void prePersistFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.ipCreatedBy = userStore.getLoggedInUser();
    this.ipUpdatedBy = userStore.getLoggedInUser();
  }

  @PreUpdate
  public void preUpdateFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.ipUpdatedBy = userStore.getLoggedInUser();
  }
}
