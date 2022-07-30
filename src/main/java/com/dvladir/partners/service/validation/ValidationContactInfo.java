package com.dvladir.partners.service.validation;

import com.dvladir.common.validation.ValidationContext;
import com.dvladir.common.validation.ValidationErrorCode;
import com.dvladir.partners.domain.ContactInfo;

import javax.validation.constraints.NotBlank;

public class ValidationContactInfo extends ContactInfo implements ValidationContext {

  public ValidationContactInfo() {}
  public ValidationContactInfo(ContactInfo contactInfo) {
    super(contactInfo);
  }


  @Override
  @NotBlank(message = ValidationErrorCode.FIELD_REQUIRED)
  public String getPhone() {
    return super.getPhone();
  }

  @Override
  @NotBlank(message = ValidationErrorCode.FIELD_REQUIRED)
  public String getEmail() {
    return super.getEmail();
  }
}
