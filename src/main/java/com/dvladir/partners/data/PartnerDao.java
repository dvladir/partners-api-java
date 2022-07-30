package com.dvladir.partners.data;

import com.dvladir.common.data.CommonRepository;
import com.dvladir.partners.api.dto.PartnerSortableFields;
import com.dvladir.partners.domain.PartnerInfo;

public interface PartnerDao extends CommonRepository<PartnerInfo, PartnerSearchParams, PartnerSortableFields> {
}
