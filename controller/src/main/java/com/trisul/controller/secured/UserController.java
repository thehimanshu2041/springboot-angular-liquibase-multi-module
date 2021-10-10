package com.trisul.controller.secured;

import com.trisul.core.constant.WebResource;
import com.trisul.core.security.swagger.ApiSecuritySchemes;
import com.trisul.internal.service.model.UserDetail;
import com.trisul.internal.service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = WebResource.SEQ_CTX_PATH + "/user")
@Tag(name = "User Service", description = "User Service.")
@Validated
public class UserController {

  @Autowired UserService userService;

  @Operation(
      summary = "User detail",
      description = "User detail",
      security = @SecurityRequirement(name = ApiSecuritySchemes.JWT_SECURITY_SCHEME))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation Successful",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserDetail.class)))
      })
  @PreAuthorize("hasAnyRole('USER','ADMIN')")
  @GetMapping
  public UserDetail getUserDetail() {
    return userService.getUserDetail();
  }
}
