package com.dvladir.common.exception;

import org.springframework.lang.Nullable;

import java.util.List;

public class PersistenceException extends BaseException {
  public PersistenceException(ErrorCode code) {
    super(code);
  }

  public PersistenceException(ErrorCode code, @Nullable List<Object> params) {
    super(code, params);
  }

  public PersistenceException(ErrorCode code, @Nullable List<Object> params, @Nullable Throwable cause) {
    super(code, params, cause);
  }

  public PersistenceException(ErrorCode code, @Nullable Throwable cause) {
    super(code, cause);
  }
}
