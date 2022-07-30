package com.dvladir.common.mappers;

import com.dvladir.common.api.dto.ValidationErrorContainerDto;
import com.dvladir.common.api.dto.ValidationErrorInfoDto;
import com.dvladir.common.domain.ValidationErrorContainer;
import com.dvladir.common.domain.ValidationErrorInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.Map;

@Mapper
public interface CommonMapper {

  final CommonMapper INSTANCE = Mappers.getMapper(CommonMapper.class);

  ValidationErrorContainerDto errorContainerToErrorContainerDto(ValidationErrorContainer validationErrorContainer);

  default ValidationErrorInfoDto errorInfoToErrorInfoDto(ValidationErrorInfo validationErrorInfo) {
    if (validationErrorInfo == null || (validationErrorInfo.getErrors().isEmpty() && validationErrorInfo.getChildren().isEmpty())) {
      return null;
    }

    final ValidationErrorInfoDto result = new ValidationErrorInfoDto();
    if (!validationErrorInfo.getErrors().isEmpty()) {
      result.setErrors(validationErrorInfo.getErrors());
    }

    if (!validationErrorInfo.getChildren().isEmpty()) {
      Map<String, ValidationErrorInfoDto> children = new HashMap<>();
      for (Map.Entry<String, ValidationErrorInfo> entry : validationErrorInfo.getChildren().entrySet()) {
        children.put(entry.getKey(), errorInfoToErrorInfoDto(entry.getValue()));
      }
      result.setChildren(children);
    }

    return result;
  }

}
