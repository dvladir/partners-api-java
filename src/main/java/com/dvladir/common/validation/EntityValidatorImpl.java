package com.dvladir.common.validation;

import com.dvladir.common.domain.ValidationErrorInfo;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.*;

@Service
public class EntityValidatorImpl implements EntityValidator {

  private final Validator validator;

  public EntityValidatorImpl() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    this.validator = validatorFactory.getValidator();
  }

  @Override
  public ValidationErrorInfo validate(ValidationContext context) {

    Set<ConstraintViolation<ValidationContext>> violations = validator.validate(context);
    if (violations.isEmpty()) {
      return null;
    }

    final ValidationErrorInfo result = new ValidationErrorInfo();

    for(ConstraintViolation<ValidationContext> v: violations) {
      ValidationErrorInfo errorContainer = result;

      final Iterator<Path.Node> nodes = v.getPropertyPath().iterator();

      Path.Node currentNode = null;
      while (nodes.hasNext()) {

        currentNode = nodes.next();

        final String prop = currentNode.toString();

        if (!errorContainer.getChildren().containsKey(prop)) {
          errorContainer.getChildren().put(prop, new ValidationErrorInfo());
        }

        errorContainer = errorContainer.getChildren().get(prop);
      }

      if (currentNode != null) {
        errorContainer.getErrors().add(v.getMessage());
      }

    }

    return result;
  }
}
