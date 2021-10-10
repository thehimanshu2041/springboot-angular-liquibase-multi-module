package com.trisul.internal.service.model;

public enum CscTypeEnum {
  COUNTRY("COUNTRY"),
  STATE("STATE"),
  CITY("CITY");

  private String code;

  CscTypeEnum(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
