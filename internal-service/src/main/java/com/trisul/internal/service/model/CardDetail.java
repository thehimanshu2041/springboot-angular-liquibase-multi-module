package com.trisul.internal.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "CardDetail", description = "Card Detail")
public class CardDetail {

  @Schema(format = "string", description = "Provide card id")
  @JsonProperty("cardID")
  private Long cardID;

  @Schema(format = "number", required = true, description = "Provide card number.")
  @NotNull(message = "Card number can't be null.")
  @JsonProperty("cardNumber")
  private Long cardNumber;

  @Schema(format = "string", required = true, description = "Provide first name.")
  @NotNull(message = "Card first name can't be null.")
  @JsonProperty("cardFirstName")
  private String cardFirstName;

  @Schema(format = "string", required = true, description = "Provide card last name.")
  @NotNull(message = "Card last name can't be null.")
  @JsonProperty("cardLastName")
  private String cardLastName;

  @Schema(format = "number", required = true, description = "Provide card expire month.")
  @NotNull(message = "Card expire month can't be null.")
  @JsonProperty("cardExpireMonth")
  private Long cardExpireMonth;

  @Schema(format = "number", required = true, description = "Provide card expire year.")
  @NotNull(message = "Card expire year can't be null.")
  @JsonProperty("cardExpireYear")
  private Long cardExpireYear;

  @Schema(format = "number", required = true, description = "Provide card cvv.")
  @NotNull(message = "Card cvv can't be null.")
  @JsonProperty("cardCvv")
  private Long cardCvv;
}
