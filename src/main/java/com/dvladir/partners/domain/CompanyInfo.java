package com.dvladir.partners.domain;

import java.util.Objects;

public class CompanyInfo {
    private String name;
    private int foundationYear;
    private int numEmployees;

    public CompanyInfo() {
    }

    public CompanyInfo(CompanyInfo companyInfo) {
        this.name = companyInfo.name;
        this.foundationYear = companyInfo.foundationYear;
        this.numEmployees = companyInfo.numEmployees;
    }

    public CompanyInfo(String name, int foundationYear, int numEmployees) {
        this.name = name;
        this.foundationYear = foundationYear;
        this.numEmployees = numEmployees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFoundationYear() {
        return foundationYear;
    }

    public void setFoundationYear(int foundationYear) {
        this.foundationYear = foundationYear;
    }

    public int getNumEmployees() {
        return numEmployees;
    }

    public void setNumEmployees(int numEmployees) {
        this.numEmployees = numEmployees;
    }

    @Override
    public String toString() {
        return "CompanyInfo{" +
                "name='" + name + '\'' +
                ", foundationYear=" + foundationYear +
                ", numEmployees=" + numEmployees +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyInfo that = (CompanyInfo) o;
        return foundationYear == that.foundationYear && numEmployees == that.numEmployees && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, foundationYear, numEmployees);
    }
}
