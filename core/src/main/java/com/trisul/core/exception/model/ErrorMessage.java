package com.trisul.core.exception.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

  @Schema(format = "number", description = "status")
  @JsonProperty("status")
  private int status;

  @Schema(format = "date-time", description = "timestamp")
  @JsonProperty("timestamp")
  private Date timestamp;

  @Schema(description = "error")
  @JsonProperty("error")
  private Error error;
}
