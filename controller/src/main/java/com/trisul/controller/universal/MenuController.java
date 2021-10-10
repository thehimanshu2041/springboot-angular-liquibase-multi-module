package com.trisul.controller.universal;

import com.trisul.core.constant.WebResource;
import com.trisul.core.security.swagger.ApiSecuritySchemes;
import com.trisul.internal.service.model.MenuDetail;
import com.trisul.internal.service.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = WebResource.CTX_PATH + "/menu")
@Tag(name = "Menu Service", description = "Menu Service.")
public class MenuController {

  @Autowired MenuService menuService;

  @Operation(
      summary = "Menu List",
      description = "It will give you list of configured menu.",
      security = @SecurityRequirement(name = ApiSecuritySchemes.JWT_SECURITY_SCHEME))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation Successful",
            content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = MenuDetail.class))))
      })
  @GetMapping
  public List<MenuDetail> getMenuList() {
    return menuService.getMenuList();
  }

  @Operation(summary = "Menu Detail", description = "It will create a new menu item.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation Successful",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = MenuDetail.class)))
      })
  @PostMapping
  public boolean createMenu(@Valid @RequestBody MenuDetail menuDetail) {
    return menuService.createMenuDetail(menuDetail);
  }
}
