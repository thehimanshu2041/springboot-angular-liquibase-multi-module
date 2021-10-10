package com.trisul.core.exception.model;

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
public class Error {

  @Schema(format = "string", description = "message")
  @JsonProperty("message")
  private String message;

  @Schema(format = "string", description = "description")
  @JsonProperty("description")
  private String description;
}
