package com.dvladir.partners.service.validation;

import com.dvladir.common.validation.ValidationContext;
import com.dvladir.common.validation.ValidationErrorCode;
import com.dvladir.partners.domain.Address;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

public class ValidationAddress extends Address implements ValidationContext {

  public ValidationAddress() {
  }
  public ValidationAddress(Address address) {
    super(address);
  }

  @Override
  @NotBlank(message = ValidationErrorCode.FIELD_REQUIRED)
  public String getIdx() {
    return super.getIdx();
  }

  @Nullable
  @Override
  @NotBlank(message = ValidationErrorCode.FIELD_REQUIRED)
  public String getHouseNumber() {
    return super.getHouseNumber();
  }

  @Nullable
  @Override
  @NotBlank(message = ValidationErrorCode.FIELD_REQUIRED)
  public String getStreet() {
    return super.getStreet();
  }

  @Nullable
  @Override
  @NotBlank(message = ValidationErrorCode.FIELD_REQUIRED)
  public String getCity() {
    return super.getCity();
  }
}
