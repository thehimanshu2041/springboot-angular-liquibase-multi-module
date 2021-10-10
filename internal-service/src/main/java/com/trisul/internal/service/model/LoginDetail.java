package com.trisul.internal.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trisul.core.bean.validation.PasswordValidator;
import com.trisul.core.bean.validation.UsernameValidator;
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
@Schema(name = "LoginDetail", description = "Login detail")
public class LoginDetail {

  @NotNull(message = "Username can't be null.")
  @UsernameValidator
  @Schema(format = "string", required = true, description = "Provide username")
  @JsonProperty("username")
  private String username;

  @NotNull(message = "Password can't be null.")
  @PasswordValidator
  @Schema(format = "string", required = true, description = "Provide password")
  @JsonProperty("password")
  private String password;
}
