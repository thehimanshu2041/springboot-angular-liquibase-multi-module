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
@Schema(name = "AddressDetail", description = "Address Detail")
public class AddressDetail {

  @Schema(format = "string", description = "Provide address id")
  @JsonProperty("addressID")
  private Long addressID;

  @Schema(format = "string", required = true, description = "Provide address 1.")
  @NotNull(message = "Address 1 can't be null.")
  @JsonProperty("addressAddress1")
  private String addressAddress1;

  @Schema(format = "string", description = "Provide address 2.")
  @JsonProperty("addressAddress2")
  private String addressAddress2;

  @Schema(format = "string", description = "Provide address 3.")
  @JsonProperty("addressAddress3")
  private String addressAddress3;

  @Schema(required = true, description = "Provide city.")
  @NotNull(message = "City can't be null.")
  @JsonProperty("addressCity")
  private CscDetail addressCity = new CscDetail();

  @Schema(required = true, description = "Provide state.")
  @NotNull(message = "State can't be null.")
  @JsonProperty("addressState")
  private CscDetail addressState = new CscDetail();

  @Schema(required = true, description = "Provide country.")
  @NotNull(message = "Country can't be null.")
  @JsonProperty("addressCountry")
  private CscDetail addressCountry = new CscDetail();
}
