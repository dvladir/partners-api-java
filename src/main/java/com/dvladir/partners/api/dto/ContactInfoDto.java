package com.dvladir.partners.api.dto;

import java.io.Serializable;

public class ContactInfoDto implements Serializable {
    private String phone;
    private String email;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
