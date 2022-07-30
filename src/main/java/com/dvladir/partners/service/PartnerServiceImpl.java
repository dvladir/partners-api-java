package com.dvladir.partners.service;

import com.dvladir.common.domain.ValidationErrorContainer;
import com.dvladir.common.domain.ValidationErrorInfo;
import com.dvladir.common.exception.ErrorCode;
import com.dvladir.common.validation.EntityValidator;
import com.dvladir.partners.api.dto.PartnerSortableFields;
import com.dvladir.common.domain.PageData;
import com.dvladir.common.domain.SortField;
import com.dvladir.partners.data.PartnerDao;
import com.dvladir.partners.data.PartnerSearchParams;
import com.dvladir.partners.domain.PartnerInfo;
import com.dvladir.common.exception.ServiceException;
import com.dvladir.partners.service.validation.ValidationPartnerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PartnerServiceImpl implements PartnerService {

  private final PartnerDao partnerDao;

  private final EntityValidator entityValidator;

  @Autowired
  public PartnerServiceImpl(PartnerDao partnerDao, EntityValidator entityValidator) {
    this.partnerDao = partnerDao;
    this.entityValidator = entityValidator;
  }

  @Nullable
  private ValidationErrorContainer validatePartner(PartnerInfo partnerInfo) {
    final ValidationErrorInfo errors = entityValidator.validate(new ValidationPartnerInfo(partnerInfo));
    return errors != null ? new ValidationErrorContainer(errors) : null;
  }

  @Override
  @Transactional
  public PageData<PartnerInfo> searchPartners(String search, int pageNum, int pageSize) throws ServiceException {
    return searchPartners(search, pageNum, pageSize, null);
  }

  @Override
  @Transactional
  public PageData<PartnerInfo> searchPartners(String search, int pageNum, int pageSize, SortField<PartnerSortableFields> sort) throws ServiceException {
    PageData<PartnerInfo> searchResult;
    try {

      final PartnerSearchParams params = new PartnerSearchParams.Builder()
          .searchQuery(search)
          .build();

      searchResult = partnerDao.findAll(pageNum, pageSize, params, sort);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTERNAL_ERROR, e);
    }

    return searchResult;
  }

  @Override
  @Transactional
  public @NonNull PartnerInfo getPartnerById(UUID id) throws ServiceException {
    PartnerInfo partnerInfo;
    try {
      PartnerSearchParams params = new PartnerSearchParams.Builder()
          .id(id)
          .build();

      partnerInfo = partnerDao.find(params);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTERNAL_ERROR, e);
    }

    if (partnerInfo == null) {
      throw new ServiceException(ErrorCode.PARTNER_NOT_FOUND, List.of(id));
    }

    return partnerInfo;
  }

  @Override
  @Transactional
  public void removePartner(UUID id) throws ServiceException {
    boolean isDeleted;
    try {
      isDeleted = partnerDao.remove(id);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTERNAL_ERROR, e);
    }

    if (!isDeleted) {
      throw new ServiceException(ErrorCode.PARTNER_NOT_FOUND, List.of(id));
    }
  }

  @Override
  @Transactional
  public void updatePartner(PartnerInfo partner) throws ServiceException {

    final ValidationErrorContainer errors = validatePartner(partner);
    if (errors != null) {
      throw new ServiceException(ErrorCode.VALIDATION_ERROR, List.of(errors));
    }

    boolean isUpdated;
    try {
      isUpdated = partnerDao.update(partner);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTERNAL_ERROR, e);
    }

    if (!isUpdated) {
      throw new ServiceException(ErrorCode.PARTNER_NOT_FOUND, List.of(partner.getId()));
    }
  }

  @Override
  @Transactional
  public UUID addPartner(PartnerInfo partner) throws ServiceException {
    final ValidationErrorContainer errors = validatePartner(partner);
    if (errors != null) {
      throw new ServiceException(ErrorCode.VALIDATION_ERROR, List.of(errors));
    }

    UUID partnerId;
    try {
      partnerId = partnerDao.add(partner);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTERNAL_ERROR, e);
    }
    return partnerId;
  }

}
