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
@Schema(name = "ManagedContentTile", description = "Managed Content Tile")
public class ManagedContentTile {

  @Schema(format = "string", description = "key")
  @JsonProperty("key")
  private String key;

  @Schema(format = "string", description = "type")
  @JsonProperty("type")
  private String type;

  @Schema(format = "string", description = "title")
  @JsonProperty("title")
  private String title;

  @Schema(format = "string", description = "description")
  @JsonProperty("description")
  private String description;

  @Schema(format = "string", description = "icon")
  @JsonProperty("icon")
  private String icon;

  @Schema(format = "boolean", description = "isDelete")
  @JsonProperty("isDelete")
  private boolean isDelete;

  @Schema(format = "boolean", description = "isAccessed")
  @JsonProperty("isAccessed")
  private boolean isAccessed;
}
