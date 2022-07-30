package com.dvladir.common.exception;

import org.springframework.lang.Nullable;

import java.util.List;

public class ApiException extends BaseException {
  public ApiException(ErrorCode code) {
    super(code);
  }

  public ApiException(ErrorCode code, @Nullable List<Object> params) {
    super(code, params);
  }

  public ApiException(ErrorCode code, @Nullable List<Object> params, @Nullable Throwable cause) {
    super(code, params, cause);
  }

  public ApiException(ErrorCode code, @Nullable Throwable cause) {
    super(code, cause);
  }
}
