package com.dvladir.common.api.dto;


public class ValidationErrorContainerDto {
  private ValidationErrorInfoDto errors;

  public ValidationErrorContainerDto() {
  }

  public ValidationErrorContainerDto(ValidationErrorInfoDto errors) {
    this.errors = errors;
  }

  public ValidationErrorInfoDto getErrors() {
    return errors;
  }

  public void setErrors(ValidationErrorInfoDto errors) {
    this.errors = errors;
  }
}
