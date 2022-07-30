package com.dvladir.common.domain;

import java.util.*;

public class ValidationErrorInfo {
  private List<String> errors = new ArrayList<>();

  private Map<String, ValidationErrorInfo> children = new HashMap<>();

  public ValidationErrorInfo() {
  }

  public List<String> getErrors() {
    return errors;
  }

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }

  public Map<String, ValidationErrorInfo> getChildren() {
    return children;
  }

  public void setChildren(Map<String, ValidationErrorInfo> children) {
    this.children = children;
  }

  @Override
  public String toString() {
    return "ErrorInfo{" +
        "errors=" + errors +
        ", children=" + children +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ValidationErrorInfo that = (ValidationErrorInfo) o;
    return Objects.equals(errors, that.errors) && Objects.equals(children, that.children);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errors, children);
  }
}
