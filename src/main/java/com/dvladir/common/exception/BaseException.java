package com.dvladir.common.exception;

import org.springframework.lang.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class BaseException extends RuntimeException {

  private static String convertCodeToMessage(
      @Nullable ErrorCode code,
      @Nullable List<Object> params) {
    String paramsString = "";
    if (params != null) {
      paramsString = params.stream()
          .map(Object::toString)
          .collect(Collectors.joining(", "));
    }
    final String msg = String.format("%s: %s", code.toString(), paramsString);
    return msg;
  }

  private String message = null;

  private ErrorCode code;

  private List<Object> params = null;

  public BaseException(ErrorCode code) {
    super((String) null);
    this.code = code;
    this.message = convertCodeToMessage(code, null);
  }

  public BaseException(ErrorCode code, @Nullable List<Object> params) {
    super((String) null);
    this.code = code;
    this.params = params;
    this.message = convertCodeToMessage(code, params);
  }

  public BaseException(ErrorCode code, @Nullable List<Object> params, @Nullable Throwable cause) {
    super((String) null, cause);
    this.code = code;
    this.params = params;
    this.message = convertCodeToMessage(code, params);
  }

  public BaseException(ErrorCode code, @Nullable Throwable cause) {
    super((String) null, cause);
    this.code = code;
    this.message = convertCodeToMessage(code, null);
  }

  @Override
  public String getMessage() {
    return message;
  }

  public ErrorCode getCode() {
    return code;
  }

  public List<Object> getParams() {
    return params;
  }
}
