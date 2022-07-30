package com.dvladir.common.validation;

import com.dvladir.common.domain.ValidationErrorInfo;

public interface EntityValidator {
  ValidationErrorInfo validate(ValidationContext context);
}
