package com.dvladir.partners.api.exception_handling;
import com.dvladir.common.api.dto.ErrorResponseDto;
import com.dvladir.common.api.dto.IdentifyDto;
import com.dvladir.common.domain.ValidationErrorContainer;
import com.dvladir.common.exception.BaseException;
import com.dvladir.common.exception.ErrorCode;
import com.dvladir.common.mappers.CommonMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ControllerAdvice
public class PartnerExceptionHandler {

  private HttpStatus determineHttpStatus(ErrorCode code) {
    return switch (code) {
      case PARTNER_NOT_FOUND -> HttpStatus.NOT_FOUND;
      case INVALID_PARTNER_TYPE, VALIDATION_ERROR, INVALID_PARAMETER -> HttpStatus.BAD_REQUEST;
      default -> HttpStatus.INTERNAL_SERVER_ERROR;
    };
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponseDto> handleException(BaseException exception) {
    final HttpStatus status = determineHttpStatus(exception.getCode());

    final ErrorResponseDto errorResponseDto = new ErrorResponseDto();
    errorResponseDto.setCode(exception.getCode().toString());

    if (exception.getParams() != null && !exception.getParams().isEmpty()) {
      switch (exception.getCode()) {
        case VALIDATION_ERROR -> {

          List<Object> params = exception.getParams()
              .stream()
              .map(p -> {
                if (p instanceof ValidationErrorContainer) {
                  return CommonMapper.INSTANCE.errorContainerToErrorContainerDto((ValidationErrorContainer) p);
                }
                return p;
              })
              .collect(Collectors.toList());

          errorResponseDto.setParams(params);
        }
        case PARTNER_NOT_FOUND -> {
          final UUID notFoundId = (UUID) exception.getParams().get(0);
          errorResponseDto.setParams(Arrays.asList(new Object[] {new IdentifyDto(notFoundId)}));
        }
        default -> {
          errorResponseDto.setParams(exception.getParams());
        }
      }
    }


    return new ResponseEntity<>(errorResponseDto, status);
  }

}
