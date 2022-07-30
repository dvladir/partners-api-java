package com.dvladir.partners.domain;

import java.util.Objects;
import java.util.UUID;

public class PartnerInfo {
    private UUID id = null;
    private Address addressInfo;
    private ContactInfo contactInfo;
    private PartnerType partnerType;
    private PersonalInfo personalInfo = null;
    private CompanyInfo companyInfo = null;

    public PartnerInfo() {
    }

    public PartnerInfo(PartnerInfo partnerInfo) {
        this.id = partnerInfo.id;
        this.partnerType = partnerInfo.partnerType;
        this.addressInfo = partnerInfo.addressInfo;
        this.contactInfo = partnerInfo.contactInfo;
        this.personalInfo = partnerInfo.personalInfo;
        this.companyInfo = partnerInfo.companyInfo;
    }

    public PartnerInfo(Address addressInfo, ContactInfo contactInfo, PersonalInfo personalInfo, UUID id) {
        this.id = id;
        this.partnerType = PartnerType.naturalPerson;
        this.addressInfo = addressInfo;
        this.contactInfo = contactInfo;
        this.personalInfo = personalInfo;
    }

    public PartnerInfo(Address addressInfo, ContactInfo contactInfo, PersonalInfo personalInfo) {
        this.partnerType = PartnerType.naturalPerson;
        this.addressInfo = addressInfo;
        this.contactInfo = contactInfo;
        this.personalInfo = personalInfo;
    }

    public PartnerInfo(Address addressInfo, ContactInfo contactInfo, CompanyInfo companyInfo, UUID id) {
        this.id = id;
        this.partnerType = PartnerType.legalEntity;
        this.addressInfo = addressInfo;
        this.contactInfo = contactInfo;
        this.companyInfo = companyInfo;
    }

    public PartnerInfo(Address addressInfo, ContactInfo contactInfo, CompanyInfo companyInfo) {
        this.partnerType = PartnerType.legalEntity;
        this.addressInfo = addressInfo;
        this.contactInfo = contactInfo;
        this.companyInfo = companyInfo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Address getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(Address addressInfo) {
        this.addressInfo = addressInfo;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public PartnerType getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(PartnerType partnerType) {
        this.partnerType = partnerType;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    @Override
    public String toString() {
        return "PartnerInfo{" +
                "id='" + id + '\'' +
                ", addressInfo=" + addressInfo +
                ", contactInfo=" + contactInfo +
                ", partnerType=" + partnerType +
                ", personalInfo=" + personalInfo +
                ", companyInfo=" + companyInfo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartnerInfo that = (PartnerInfo) o;
        return Objects.equals(id, that.id) && Objects.equals(addressInfo, that.addressInfo) && Objects.equals(contactInfo, that.contactInfo) && partnerType == that.partnerType && Objects.equals(personalInfo, that.personalInfo) && Objects.equals(companyInfo, that.companyInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, addressInfo, contactInfo, partnerType, personalInfo, companyInfo);
    }
}
