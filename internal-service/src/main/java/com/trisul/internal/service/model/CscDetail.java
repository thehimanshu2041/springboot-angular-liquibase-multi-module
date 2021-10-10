package com.trisul.internal.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "CscDetail", description = "Csc Detail")
public class CscDetail {

  @Schema(format = "number", required = true, description = "Provide id")
  @JsonProperty("cscID")
  private Long cscID;

  @Schema(format = "string", required = true, description = "Provide key")
  @JsonProperty("cscKey")
  private String cscKey;

  @Schema(format = "string", required = true, description = "Provide short description")
  @JsonProperty("cscShortDescription")
  private String cscShortDescription;

  @Schema(format = "string", required = true, description = "Provide description")
  @JsonProperty("cscDescription")
  private String cscDescription;

  @Schema(format = "number", required = true, description = "Provide priority")
  @JsonProperty("cscPriority")
  private Long cscPriority;

  @Schema(format = "number", required = true, description = "Provide code id")
  @JsonProperty("cscCodeID")
  private Long cscCodeID;

  @NotNull(message = "CSC type can't be null.")
  @Schema(required = true, description = "Provide type")
  @JsonProperty("cscType")
  private CscTypeEnum cscType;
}
