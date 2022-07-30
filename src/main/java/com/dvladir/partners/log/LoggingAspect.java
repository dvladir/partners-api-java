package com.dvladir.partners.log;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

  private final Logger LOGGER = LoggerFactory.getLogger(getClass());

  @AfterThrowing(
      value = "execution(* com.dvladir.partners.*.*.*(..))",
      throwing = "ex"
  )
  public void exceptionHappened(Exception ex) {
    LOGGER.error("ERROR", ex);
  }

}
