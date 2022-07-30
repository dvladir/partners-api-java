package com.dvladir.partners.service.validation;

import com.dvladir.common.validation.ValidationContext;
import com.dvladir.common.validation.ValidationErrorCode;
import com.dvladir.partners.domain.*;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ValidationPartnerInfo extends PartnerInfo implements ValidationContext {
  public ValidationPartnerInfo(PartnerInfo partnerInfo) {
    super();
    this.setId(partnerInfo.getId());
    this.setPartnerType(partnerInfo.getPartnerType());

    Address address = partnerInfo.getAddressInfo();
    address = address != null ? new ValidationAddress(address) : new ValidationAddress();
    this.setAddressInfo(address);

    ContactInfo contactInfo = partnerInfo.getContactInfo();
    contactInfo = contactInfo != null ? new ValidationContactInfo(contactInfo) : new ValidationContactInfo();
    this.setContactInfo(contactInfo);

    if (this.getPartnerType() == PartnerType.naturalPerson) {
      PersonalInfo personalInfo = partnerInfo.getPersonalInfo();
      personalInfo = personalInfo != null ? new ValidationPersonalInfo(personalInfo) : new ValidationPersonalInfo();
      this.setPersonalInfo(personalInfo);
    }

    if (this.getPartnerType() == PartnerType.legalEntity) {
      CompanyInfo companyInfo = partnerInfo.getCompanyInfo();
      companyInfo = companyInfo != null ? new ValidationCompanyInfo(companyInfo) : new ValidationCompanyInfo();
      this.setCompanyInfo(companyInfo);
    }
  }

  @Override
  @NotNull(message = ValidationErrorCode.FIELD_REQUIRED)
  public PartnerType getPartnerType() {
    return super.getPartnerType();
  }

  @Override
  @Valid
  public Address getAddressInfo() {
    return super.getAddressInfo();
  }

  @Override
  @Valid
  public ContactInfo getContactInfo() {
    return super.getContactInfo();
  }

  @Override
  @Nullable
  @Valid
  public CompanyInfo getCompanyInfo() {
    return super.getCompanyInfo();
  }

  @Override
  @Nullable
  @Valid
  public PersonalInfo getPersonalInfo() {
    return super.getPersonalInfo();
  }
}
