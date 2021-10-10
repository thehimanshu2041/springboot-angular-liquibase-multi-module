package com.trisul.internal.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "CodeDetail", description = "Code Detail")
public class CodeDetail {

  @Schema(format = "number", required = true, description = "Provide code id")
  @JsonProperty("codeID")
  private Long codeID;

  @Schema(format = "string", required = true, description = "Provide code key")
  @JsonProperty("codeKey")
  private String codeKey;

  @Schema(format = "string", required = true, description = "Provide code type")
  @JsonProperty("codeType")
  private String codeType;

  @Schema(format = "string", required = true, description = "Provide code short description")
  @JsonProperty("codeShortDescription")
  private String codeShortDescription;

  @Schema(format = "string", required = true, description = "Provide code description")
  @JsonProperty("codeDescription")
  private String codeDescription;

  @Schema(format = "string", required = true, description = "Provide code priority")
  @JsonProperty("codePriority")
  private Long codePriority;
}
