package com.trisul.core.security.jwt;

public enum RoleTypeEnum {
  USER("USER"),
  ADMIN("ADMIN"),
  USER_CREATE("USER_CREATE"),
  USER_UPDATE("USER_UPDATE"),
  USER_DELETE("USER_DELETE");

  private String code;

  RoleTypeEnum(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
