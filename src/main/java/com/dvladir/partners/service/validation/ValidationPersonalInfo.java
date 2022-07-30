package com.dvladir.partners.service.validation;

import com.dvladir.common.validation.ValidationContext;
import com.dvladir.common.validation.ValidationErrorCode;
import com.dvladir.partners.domain.Gender;
import com.dvladir.partners.domain.PersonalInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ValidationPersonalInfo extends PersonalInfo implements ValidationContext {

  public ValidationPersonalInfo(){};

  public ValidationPersonalInfo(PersonalInfo personalInfo) {
    super(personalInfo);
  }

  @Override
  @NotBlank(message = ValidationErrorCode.FIELD_REQUIRED)
  public String getFirstName() {
    return super.getFirstName();
  }

  @Override
  @NotBlank(message = ValidationErrorCode.FIELD_REQUIRED)
  public String getLastName() {
    return super.getLastName();
  }

  @Override
  @NotNull(message = ValidationErrorCode.FIELD_REQUIRED)
  public Gender getGender() {
    return super.getGender();
  }

  @Override
  @NotNull(message = ValidationErrorCode.FIELD_REQUIRED)
  public Date getBirthDate() {
    return super.getBirthDate();
  }
}
