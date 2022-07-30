package com.dvladir.partners.api.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.UUID;

public class PartnerInfoDto implements Serializable {
    private UUID id;
    private AddressDto addressInfo;
    private ContactInfoDto contactInfo;
    private String partnerType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PersonalInfoDto personalInfo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CompanyInfoDto companyInfo;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AddressDto getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressDto addressInfo) {
        this.addressInfo = addressInfo;
    }

    public ContactInfoDto getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfoDto contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public PersonalInfoDto getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfoDto personalInfo) {
        this.personalInfo = personalInfo;
    }

    public CompanyInfoDto getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfoDto companyInfo) {
        this.companyInfo = companyInfo;
    }
}
