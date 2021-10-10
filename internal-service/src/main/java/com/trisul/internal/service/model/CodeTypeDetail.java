package com.trisul.internal.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "CodeTypeDetail", description = "Code Type Detail")
public class CodeTypeDetail {

  @Schema(format = "number", required = true, description = "Provide code type id")
  @JsonProperty("codeTypeID")
  private Long codeTypeID;

  @Schema(format = "string", required = true, description = "Provide code type key")
  @JsonProperty("codeTypeKey")
  private String codeTypeKey;

  @Schema(format = "string", required = true, description = "Provide code type short description")
  @JsonProperty("codeTypeShortDescription")
  private String codeTypeShortDescription;

  @Schema(format = "string", required = true, description = "Provide code type description")
  @JsonProperty("codeTypeDescription")
  private String codeTypeDescription;

  @Schema(format = "number", required = true, description = "Provide code type priority")
  @JsonProperty("codeTypePriority")
  private Long codeTypePriority;

  @JsonProperty("codes")
  private List<CodeDetail> codes = new ArrayList<>();
}
