package com.trisul.controller.universal;

import com.trisul.core.constant.WebResource;
import com.trisul.internal.service.model.AuthenticationDetail;
import com.trisul.internal.service.model.LoginDetail;
import com.trisul.internal.service.model.UserDetail;
import com.trisul.internal.service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = WebResource.CTX_PATH + "/auth")
@Tag(name = "Auth Service", description = "Auth Service.")
@Validated
public class AuthController {

  @Autowired UserService userService;

  @Operation(summary = "User login", description = "User login")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation Successful",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AuthenticationDetail.class)))
      })
  @PostMapping(value = "login")
  public AuthenticationDetail doLogin(@Valid @RequestBody LoginDetail loginDetail) {
    return userService.doLogin(loginDetail);
  }

  @Operation(
      summary = "User Registration",
      description = "It will create a new user with user role.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation Successful",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Boolean.class)))
      })
  @PostMapping(value = "registration")
  public Boolean doRegistration(@Valid @RequestBody UserDetail userDetail) {
    return userService.createUser(userDetail);
  }
}
