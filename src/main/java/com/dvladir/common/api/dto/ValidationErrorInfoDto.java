package com.dvladir.common.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ValidationErrorInfoDto implements Serializable {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<String> errors;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Map<String, ValidationErrorInfoDto> children;

  public List<String> getErrors() {
    return errors;
  }

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }

  public Map<String, ValidationErrorInfoDto> getChildren() {
    return children;
  }

  public void setChildren(Map<String, ValidationErrorInfoDto> children) {
    this.children = children;
  }

  @Override
  public int hashCode() {
    return Objects.hash(errors, children);
  }
}
