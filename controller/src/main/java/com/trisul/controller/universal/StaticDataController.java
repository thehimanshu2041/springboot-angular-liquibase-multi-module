package com.trisul.controller.universal;

import com.trisul.core.constant.WebResource;
import com.trisul.internal.service.model.CodeDetail;
import com.trisul.internal.service.model.CscDetail;
import com.trisul.internal.service.model.ManagedContent;
import com.trisul.internal.service.service.StaticDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = WebResource.CTX_PATH + "/static-data")
@Tag(name = "Static Data Service", description = "Static Data Service.")
public class StaticDataController {

  @Autowired StaticDataService staticDataService;

  @Operation(summary = "Code", description = "It will give you code detail by code key.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation Successful",
            content = @Content(schema = @Schema(implementation = CodeDetail.class)))
      })
  @GetMapping(value = "code/{codeKey}")
  public CodeDetail getCode(@NotNull @PathVariable String codeKey) {
    return staticDataService.getCodeByCodeKey(codeKey);
  }

  @Operation(summary = "Code", description = "It will give you list of code by code type.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation Successful",
            content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = CodeDetail.class))))
      })
  @GetMapping(value = "codes/{codeType}")
  public List<CodeDetail> getCodeType(@NotNull @PathVariable String codeType) {
    return staticDataService.getCodesByCodeType(codeType);
  }

  @Operation(summary = "Tile", description = "It will give you list of configured Tile.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation Successful",
            content =
                @Content(
                    array = @ArraySchema(schema = @Schema(implementation = ManagedContent.class))))
      })
  @GetMapping(value = "tiles")
  public List<ManagedContent> getConfiguredTile() {
    return staticDataService.getConfiguredTiles();
  }

  @Operation(summary = "Countries", description = "It will give you list of countries.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation Successful",
            content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = CscDetail.class))))
      })
  @GetMapping(value = "countries")
  public List<CscDetail> getCountries() {
    return staticDataService.getCountries();
  }

  @Operation(
      summary = "Country",
      description = "It will give you country detail by country code key.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation Successful",
            content = @Content(schema = @Schema(implementation = CscDetail.class)))
      })
  @GetMapping(value = "country/{countryKey}")
  public CscDetail getCountryByCountryKey(@NotNull @PathVariable String countryKey) {
    return staticDataService.getCountryByCountryKey(countryKey);
  }

  @Operation(summary = "States", description = "It will give you list of States.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation Successful",
            content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = CscDetail.class))))
      })
  @GetMapping(value = "states/{countryId}")
  public List<CscDetail> getStatesByCountryId(@NotNull @PathVariable Long countryId) {
    return staticDataService.getStatesByCountryId(countryId);
  }

  @Operation(summary = "Cities", description = "It will give you list of Cities.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operation Successful",
            content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = CscDetail.class))))
      })
  @GetMapping(value = "cities/{stateId}")
  public List<CscDetail> getCitiesByStateId(@NotNull @PathVariable Long stateId) {
    return staticDataService.getCitiesByStateId(stateId);
  }
}
