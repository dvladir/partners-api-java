package com.dvladir.partners.domain;

import java.util.Date;
import java.util.Objects;

public class PersonalInfo {
    private String firstName;
    private String lastName;
    private String middleName;
    private Date birthDate;
    private Gender gender;

    public PersonalInfo() {
    }

    public PersonalInfo(PersonalInfo personalInfo) {
        this.firstName = personalInfo.firstName;
        this.lastName = personalInfo.lastName;
        this.middleName = personalInfo.middleName;
        this.birthDate = personalInfo.birthDate;
        this.gender = personalInfo.gender;
    }

    public PersonalInfo(String firstName, String lastName, String middleName, Date birthDate, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "PersonalInfo{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalInfo that = (PersonalInfo) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(middleName, that.middleName) && Objects.equals(birthDate, that.birthDate) && gender == that.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, middleName, birthDate, gender);
    }
}
