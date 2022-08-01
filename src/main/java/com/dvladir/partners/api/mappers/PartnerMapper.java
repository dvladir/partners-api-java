package com.dvladir.partners.api.mappers;

import com.dvladir.common.api.dto.PageDataDto;
import com.dvladir.common.api.dto.SortFieldDto;
import com.dvladir.partners.api.dto.*;
import com.dvladir.common.domain.PageData;
import com.dvladir.common.domain.SortField;
import com.dvladir.partners.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper
public interface PartnerMapper {

  final PartnerMapper INSTANCE = Mappers.getMapper(PartnerMapper.class);

  final static String DATE_FORMAT = "dd/MM/yyyy";

  @Named("stringToDate")
  public static Date stringToDate(@Nullable String strDate) throws ParseException {
      if (strDate == null || strDate.trim().isEmpty()) {
        return null;
      }
      return new SimpleDateFormat(DATE_FORMAT).parse(strDate);
  }

  @Named("dateToString")
  public static String dateToString(@Nullable Date date) {
      if (date == null) {
        return null;
      }
      return new SimpleDateFormat(DATE_FORMAT).format(date);
  }

  @Named("fullAddress")
  public static String fullAddress(Address address) {
    return Stream.of(
            address.getStreet(),
            address.getHouseNumber(),
            address.getIdx()
        )
        .filter(value -> Objects.nonNull(value) && !value.trim().isEmpty())
        .collect(Collectors.joining(" "));
  }

  @Named("displayName")
  public static String displayName(PartnerInfo partnerInfo) {

    if (partnerInfo.getPartnerType() == PartnerType.legalEntity) {
      return partnerInfo.getCompanyInfo().getName();
    }

    final PersonalInfo personalInfo = partnerInfo.getPersonalInfo();

    return Stream.of(
            personalInfo.getLastName(),
            personalInfo.getFirstName(),
            personalInfo.getMiddleName()
        )
        .filter(value -> Objects.nonNull(value) && !value.trim().isEmpty())
        .collect(Collectors.joining(" "));
  }

  AddressDto addressToAddressDto(Address address);

  Address addressDtoToAddress(AddressDto addressDto);

  CompanyInfoDto companyInfoToCompanyDto(CompanyInfo companyInfo);

  CompanyInfo companyInfoDtoToCompany(CompanyInfoDto companyInfoDto);

  ContactInfoDto contactInfoToContactInfoDto(ContactInfo contactInfo);

  ContactInfo contactInfoDtoToContactInfo(ContactInfoDto contactInfoDto);

  @Mappings({
      @Mapping(target = "birthDate", qualifiedByName = "dateToString")
  })
  PersonalInfoDto personalInfoToPersonalInfoDto(PersonalInfo personalInfo);

  @Mappings({
      @Mapping(target = "birthDate", qualifiedByName = "stringToDate")
  })
  PersonalInfo personalInfoDtoToPersonalInfo(PersonalInfoDto personalInfoDto);

  PartnerInfoDto partnerInfoToPartnerDto(PartnerInfo partner);

  PartnerInfo partnerInfoDtoToPartner(PartnerInfoDto partnerInfoDto);

  PageDataDto<PartnerHeaderDto> partnerPageToPartnerHeaderPageDto(PageData<PartnerInfo> pagePartner);

  SortField<PartnerSortableFields> sortFieldDtoToSortField(SortFieldDto sortFieldDto);

  @Mappings({
      @Mapping(target = "displayName", source = "partnerInfo", qualifiedByName = "displayName"),
      @Mapping(target = "address", source = "partnerInfo.addressInfo", qualifiedByName = "fullAddress"),
      @Mapping(target = "city", source = "partnerInfo.addressInfo.city"),
      @Mapping(target = "email", source = "partnerInfo.contactInfo.email")
  })
  PartnerHeaderDto partnerInfoToPartnerHeaderDto(PartnerInfo partnerInfo);
}
