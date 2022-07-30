package com.dvladir.common.api.dto;

import java.util.List;

public class ErrorResponseDto {
  private String code;
  private List<Object> params;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List<Object> getParams() {
    return params;
  }

  public void setParams(List<Object> params) {
    this.params = params;
  }
}
