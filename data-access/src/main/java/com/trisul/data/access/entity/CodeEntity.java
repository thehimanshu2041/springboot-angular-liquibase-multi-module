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
@Table(name = "CODE_ENTITY")
public class CodeEntity {

  @Id
  @SequenceGenerator(name = "CODE_ENTITY_SEQ", sequenceName = "CODE_ENTITY_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODE_ENTITY_SEQ")
  @Column(name = "CODE_ID", insertable = false, updatable = false)
  private Long codeID;

  @Column(name = "CODE_KEY", nullable = false, unique = true)
  private String codeKey;

  @Column(name = "CODE_SHORT_DESCRIPTION", nullable = false)
  private String codeShortDescription;

  @Column(name = "CODE_DESCRIPTION", nullable = false)
  private String codeDescription;

  @Column(name = "CODE_IS_AVAILABLE", nullable = false)
  private Boolean codeIsAvailable;

  @Column(name = "CODE_PRIORITY")
  private Long codePriority;

  @Column(name = "CODE_CREATED_BY", nullable = false)
  private String codeCreatedBy;

  @Column(name = "CODE_UPDATED_BY", nullable = false)
  private String codeUpdatedBy;

  @CreationTimestamp
  @Column(name = "CODE_CREATED_DATE_TIME", nullable = false)
  private Date codeCreatedDateTime;

  @UpdateTimestamp
  @Column(name = "CODE_MODIFIED_DATE_TIME", nullable = false)
  private Date codeModifiedDateTime;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CODE_CODE_TYPE_ID")
  private CodeTypeEntity codeType;

  @PrePersist
  private void prePersistFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.codeCreatedBy = userStore.getLoggedInUser();
    this.codeUpdatedBy = userStore.getLoggedInUser();
    this.codeIsAvailable = true;
  }

  @PreUpdate
  public void preUpdateFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.codeUpdatedBy = userStore.getLoggedInUser();
  }
}
