package com.template.core.model.auth;

import com.template.core.model.BaseEntity;
import com.template.core.model.Person;
import com.template.core.model.auth.AuthVendor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Credential
extends BaseEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private AuthVendor vendor;
    private String vendorId;
    private String password;
    private String salt;
    @ManyToOne
    private Person person;

    public Credential() {
    }

    public Credential(AuthVendor vendor, String vendorId, String password) {
        this.vendor = vendor;
        this.vendorId = vendorId;
        this.password = password;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AuthVendor getVendor() {
        return this.vendor;
    }

    public void setVendor(AuthVendor vendor) {
        this.vendor = vendor;
    }

    public String getVendorId() {
        return this.vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}