package com.dvladir.common.api.dto;

import com.dvladir.common.domain.SortType;
import com.dvladir.common.exception.ApiException;
import com.dvladir.common.exception.ErrorCode;
import org.springframework.lang.Nullable;

import java.io.Serializable;

public class SortFieldDto implements Serializable {
  private String field;
  private String sortType;

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getSortType() {
    return sortType;
  }

  public void setSortType(String sortType) {
    this.sortType = sortType;
  }

  public static @Nullable SortFieldDto parse(@Nullable String sort) throws ApiException {
    if (sort == null) {
      return null;
    }
    final String[] parts = sort.split(";");
    final String field = parts[0];

    String sortType = SortType.asc.toString();
    if (parts.length > 1) {
      if (!parts[1].equals(SortType.asc.toString()) && !parts[1].equals(SortType.desc.toString())) {
        throw new ApiException(ErrorCode.INVALID_PARAMETER);
      }
      sortType = parts[1];
    }

    final SortFieldDto result = new SortFieldDto();
    result.setField(field);
    result.setSortType(sortType);
    return result;
  }
}
