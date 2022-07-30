package com.dvladir.partners.data;

import java.util.UUID;

public class PartnerSearchParams {

  public static class Builder {
    private UUID id = null;
    private String searchQuery = null;

    public Builder id(UUID id) {
      this.id = id;
      return this;
    }

    public Builder searchQuery(String searchQuery) {
      this.searchQuery = searchQuery;
      return this;
    }

    public PartnerSearchParams build() {
      PartnerSearchParams params = new PartnerSearchParams();
      params.id = this.id;
      params.searchQuery = this.searchQuery;
      return params;
    }
  }

  private UUID id;

  private String searchQuery;

  private PartnerSearchParams() {
  }

  public UUID getId() {
    return id;
  }

  public String getSearchQuery() {
    return searchQuery;
  }
}
