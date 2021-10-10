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
@Table(name = "TILE_ENTITY")
public class TileEntity {

  @Id
  @SequenceGenerator(name = "TILE_ENTITY_SEQ", sequenceName = "TILE_ENTITY_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TILE_ENTITY_SEQ")
  @Column(name = "TILE_ID", insertable = false, updatable = false)
  private Long tileID;

  @Column(name = "TILE_KEY", nullable = false, unique = true)
  private String tileKey;

  @Column(name = "TILE_SHORT_DESCRIPTION", nullable = false)
  private String tileShortDescription;

  @Column(name = "TILE_DESCRIPTION", nullable = false)
  private String tileDescription;

  @Column(name = "TILE_TYPE", nullable = false)
  private String tileType;

  @Column(name = "TILE_COMPONENT")
  private String tileComponent;

  @Column(name = "TILE_ICON", nullable = false)
  private String tileIcon;

  @Column(name = "TILE_IS_ACCESSED", nullable = false)
  private Boolean tileIsAccessed;

  @Column(name = "TILE_IS_DELETE", nullable = false)
  private Boolean tileIsDelete;

  @Column(name = "TILE_PRIORITY")
  private Long tilePriority;

  @Column(name = "TILE_CREATED_BY", nullable = false)
  private String tileCreatedBy;

  @Column(name = "TILE_UPDATED_BY", nullable = false)
  private String tileUpdatedBy;

  @CreationTimestamp
  @Column(name = "TILE_CREATED_DATE_TIME", nullable = false)
  private Date tileCreatedDateTime;

  @UpdateTimestamp
  @Column(name = "TILE_MODIFIED_DATE_TIME", nullable = false)
  private Date tileModifiedDateTime;

  @PrePersist
  private void prePersistFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.tileCreatedBy = userStore.getLoggedInUser();
    this.tileUpdatedBy = userStore.getLoggedInUser();
    this.tileIsAccessed = true;
    this.tileIsDelete = false;
  }

  @PreUpdate
  public void preUpdateFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.tileUpdatedBy = userStore.getLoggedInUser();
  }
}
