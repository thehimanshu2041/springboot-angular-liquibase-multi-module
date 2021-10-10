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
@Schema(name = "ManagedContent", description = "Managed Content")
public class ManagedContent {

  @Schema(format = "string", description = "html")
  @JsonProperty("html")
  private String html;

  @Schema(description = "Managed content tile")
  @JsonProperty("tile")
  private ManagedContentTile tile;
}
