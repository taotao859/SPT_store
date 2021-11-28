package com.evan.firstspring.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})

public class customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    int customerId;
    @Column(name = "customer_password")
    String customerPassword;
    @Column(name = "customer_name")
    String customerName;
    @Column(name = "customer_contact")
    String customerContact;
    @Column(name = "customer_score")
    int customerScore;
    @Column(name = "customer_tag")
    int customerTag;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public int getCustomerScore() {
        return customerScore;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerTag() {
        return customerTag;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerScore(int customerScore) {
        this.customerScore = customerScore;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerTag(int customerTag) {
        this.customerTag = customerTag;
    }
}
