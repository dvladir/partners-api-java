package com.dvladir.common.domain;

import java.util.Objects;

public class ValidationErrorContainer {
  private ValidationErrorInfo errors;

  public ValidationErrorContainer() {
  }

  public ValidationErrorContainer(ValidationErrorInfo errors) {
    this.errors = errors;
  }

  public ValidationErrorInfo getErrors() {
    return errors;
  }

  public void setErrors(ValidationErrorInfo errors) {
    this.errors = errors;
  }

  @Override
  public String toString() {
    return "ValidationErrorContainer{" +
        "errors=" + errors +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ValidationErrorContainer that = (ValidationErrorContainer) o;
    return Objects.equals(errors, that.errors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errors);
  }
}
