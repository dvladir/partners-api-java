package com.dvladir.common.exception;

import com.dvladir.common.exception.BaseException;
import com.dvladir.common.exception.ErrorCode;
import org.springframework.lang.Nullable;

import java.util.List;

public class ServiceException extends BaseException {
  public ServiceException(ErrorCode code) {
    super(code);
  }

  public ServiceException(ErrorCode code, @Nullable List<Object> params) {
    super(code, params);
  }

  public ServiceException(ErrorCode code, @Nullable List<Object> params, @Nullable Throwable cause) {
    super(code, params, cause);
  }

  public ServiceException(ErrorCode code, @Nullable Throwable cause) {
    super(code, cause);
  }
}
