package com.dvladir.partners.domain;

import org.springframework.lang.Nullable;

import java.util.Objects;

public class Address {

    private String city;

    private String street;

    private String houseNumber;

    private String idx;

    public Address() {
    }

    public Address(Address address) {
        this.city = address.city;
        this.street = address.street;
        this.houseNumber = address.houseNumber;
        this.idx = address.idx;
    }

    public Address(String city, String street, String houseNumber, String idx) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.idx = idx;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", idx='" + idx + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(houseNumber, address.houseNumber) && Objects.equals(idx, address.idx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, houseNumber, idx);
    }
}
