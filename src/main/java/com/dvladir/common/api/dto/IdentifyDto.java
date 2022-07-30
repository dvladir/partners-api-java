package com.dvladir.common.api.dto;

import java.util.UUID;

public class IdentifyDto {
  private UUID id;

  public IdentifyDto() {
  }

  public IdentifyDto(UUID id) {
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
}
