package com.dvladir.partners.service;

import com.dvladir.partners.api.dto.PartnerSortableFields;
import com.dvladir.common.domain.PageData;
import com.dvladir.common.domain.SortField;
import com.dvladir.partners.domain.PartnerInfo;
import com.dvladir.common.exception.ServiceException;
import org.springframework.lang.NonNull;

import java.util.UUID;

public interface PartnerService {
  PageData<PartnerInfo> searchPartners(String search, int pageNum, int pageSize) throws ServiceException;
  PageData<PartnerInfo> searchPartners(String search, int pageNum, int pageSize, SortField<PartnerSortableFields> sort) throws ServiceException;
  @NonNull PartnerInfo getPartnerById(UUID id) throws ServiceException;
  void removePartner(UUID id) throws ServiceException;
  void updatePartner(PartnerInfo partner) throws ServiceException;
  UUID addPartner(PartnerInfo partner) throws ServiceException;

}
