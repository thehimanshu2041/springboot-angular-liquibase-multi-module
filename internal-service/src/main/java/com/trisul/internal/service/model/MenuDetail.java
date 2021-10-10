package com.trisul.internal.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "MenuDetail", description = "Menu Detail")
public class MenuDetail {

  @Schema(format = "number", required = false, description = "Provide menuID.")
  @JsonProperty("menuID")
  private Long menuID;

  @Schema(format = "number", required = true, description = "Provide parent menuID.")
  @NotNull(message = "Parent ID can't be null.")
  @JsonProperty("menuParentID")
  private Long menuParentID;

  @Schema(format = "string", required = true, description = "Provide menu name.")
  @Size(min = 2, max = 50, message = "Menu name should be between 2 and 50 characters.")
  @NotNull(message = "Menu name can't be null.")
  @JsonProperty("menuName")
  private String menuName;

  @Schema(format = "string", required = true, description = "Provide menu path.")
  @Size(min = 2, max = 50, message = "Menu path should be between 2 and 50 characters.")
  @NotNull(message = "Menu path can't be null.")
  @JsonProperty("menuPath")
  private String menuPath;

  @Schema(format = "string", required = true, description = "Provide menu description.")
  @Size(min = 5, max = 50, message = "Menu description should be between 5 and 50 characters.")
  @NotNull(message = "Menu description can't be null.")
  @JsonProperty("menuDescription")
  private String menuDescription;

  @Schema(format = "string", required = true, description = "Provide menu icon.")
  @Size(min = 2, max = 50, message = "Menu icon should be between 2 and 50 characters.")
  @NotNull(message = "Menu icon can't be null.")
  @JsonProperty("menuIcon")
  private String menuIcon;

  @Schema(format = "string", required = true, description = "Provide menu key.")
  @Size(min = 2, max = 50, message = "Menu key should be between 2 and 50 characters.")
  @NotNull(message = "Menu key can't be null.")
  @JsonProperty("menuKey")
  private String menuKey;

  @Schema(format = "boolean", required = true, description = "Provide menu active status.")
  @NotNull(message = "Menu active status can't be null.")
  @JsonProperty("menuIsActive")
  private Boolean menuIsActive;

  @Schema(format = "boolean", required = true, description = "Provide menu deleted status.")
  @NotNull(message = "Menu deleted status can't be null.")
  @JsonProperty("menuIsDeleted")
  private Boolean menuIsDeleted;

  @Schema(format = "boolean", required = true, description = "Provide menu admin status.")
  @NotNull(message = "Menu admin status can't be null.")
  @JsonProperty("menuIsAdmin")
  private Boolean menuIsAdmin;

  @Schema(format = "boolean", required = true, description = "Provide menu visibility status.")
  @NotNull(message = "Menu visibility status can't be null.")
  @JsonProperty("menuIsVisible")
  private Boolean menuIsVisible;

  @Schema(format = "boolean", required = true, description = "Provide menu auth status.")
  @NotNull(message = "Menu auth status can't be null.")
  @JsonProperty("menuIsAuthReq")
  private Boolean menuIsAuthReq;

  @Schema(format = "number", required = true, description = "Provide menu priority.")
  @JsonProperty("menuPriority")
  private Long menuPriority;

  @JsonProperty("children")
  private List<MenuDetail> children = new ArrayList<>();
}
