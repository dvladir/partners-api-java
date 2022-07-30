package com.dvladir.partners.service.validation;

import com.dvladir.common.validation.ValidationContext;
import com.dvladir.common.validation.ValidationErrorCode;
import com.dvladir.partners.domain.CompanyInfo;

import javax.validation.constraints.NotEmpty;

public class ValidationCompanyInfo extends CompanyInfo implements ValidationContext {

  public ValidationCompanyInfo() {}
  public ValidationCompanyInfo(CompanyInfo companyInfo) {
    super(companyInfo);
  }


  @Override
  @NotEmpty(message = ValidationErrorCode.FIELD_REQUIRED)
  public String getName() {
    return super.getName();
  }
}
