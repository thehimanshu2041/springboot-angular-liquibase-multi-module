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
@Table(name = "CARD_ENTITY")
public class CardEntity {

  @Id
  @SequenceGenerator(name = "CARD_ENTITY_SEQ", sequenceName = "CARD_ENTITY_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARD_ENTITY_SEQ")
  @Column(name = "CARD_ID", insertable = false, updatable = false)
  private Long cardID;

  @Column(name = "CARD_NUMBER", nullable = false)
  private Long cardNumber;

  @Column(name = "CARD_FIRST_NAME", nullable = false)
  private String cardFirstName;

  @Column(name = "CARD_LAST_NAME")
  private String cardLastName;

  @Column(name = "CARD_EXPIRE_MONTH", nullable = false)
  private Long cardExpireMonth;

  @Column(name = "CARD_EXPIRE_YEAR", nullable = false)
  private Long cardExpireYear;

  @Column(name = "CARD_CVV", nullable = false)
  private Long cardCvv;

  @Column(name = "CARD_IS_ACTIVE", nullable = false)
  private Boolean cardIsActive;

  @Column(name = "CARD_CREATED_BY", nullable = false)
  private String cardCreateBy;

  @Column(name = "CARD_UPDATED_BY", nullable = false)
  private String cardUpdatedBy;

  @CreationTimestamp
  @Column(name = "CARD_CREATED_DATE_TIME", nullable = false)
  private Date cardCreatedDateTime;

  @UpdateTimestamp
  @Column(name = "CARD_MODIFIED_DATE_TIME", nullable = false)
  private Date cardModifiedDateTime;

  @JsonIgnore
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CARD_USER_ID")
  private UserEntity userEntity;

  @PrePersist
  private void prePersistFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.cardCreateBy = userStore.getLoggedInUser();
    this.cardUpdatedBy = userStore.getLoggedInUser();
    this.cardIsActive = true;
  }

  @PreUpdate
  public void preUpdateFunction() {
    UserStoreImpl userStore = new UserStoreImpl();
    this.cardUpdatedBy = userStore.getLoggedInUser();
  }
}
