package com.trisul.data.access.entity;

import com.trisul.core.security.user.UserStoreImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
@Table(name = "CODE_TYPE_ENTITY")
public class CodeTypeEntity {

  @Id
  @SequenceGenerator(
      name = "CODE_TYPE_ENTITY_SEQ",
      sequenceName = "CODE_TYPE_ENTITY_SEQ",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODE_TYPE_ENTITY_SEQ")
  @Column(name = "CODE_TYPE_ID", insertable = false, updatable = false)
  private Long codeTypeID;

  @Column(name = "CODE_TYPE_KEY", nullable = false, unique = true)
  private String codeTypeKey;

  @Column(name = "CODE_TYPE_SHORT_DESCRIPTION", nullable = false)
  private String codeTypeShortDescription;

  @Column(name = "CODE_TYPE_DESCRIPTION", nullable = false)
  private String codeTypeDescription;

  @Column(name = "CODE_TYPE_IS_AVAILABLE", nullable = false)
  private Boolean codeTypeIsAvailable;

  @Column(name = "CODE_TYPE_PRIORITY")
  private Long codeTypePriority;

  @Column(name = "CODE_TYPE_CREATED_BY", nullable = false)
  private String codeTypeCreatedBy;

  @Column(name = "CODE_TYPE_UPDATED_BY", nullable = false)
  private String codeTypeUpdatedBy;

  @CreationTimestamp
  @Column(name = "CODE_TYPE_CREATED_DATE_TIME", nullable = false)
  private Date codeTypeCreatedDateTime;

  @UpdateTimestamp
  @Column(name = "CODE_TYPE_MODIFIED_DATE_TIME", nullable = false)
  private Date codeTypeModifiedDateTime;

  @OneToMany(mappedBy = "codeType", cascade = CascadeType.ALL)
  private List<CodeEntity> codes = new ArrayList<>();

  @PrePersist
  private void prePersistFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.codeTypeCreatedBy = userStore.getLoggedInUser();
    this.codeTypeUpdatedBy = userStore.getLoggedInUser();
    this.codeTypeIsAvailable = true;
  }

  @PreUpdate
  public void preUpdateFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.codeTypeUpdatedBy = userStore.getLoggedInUser();
  }
}
