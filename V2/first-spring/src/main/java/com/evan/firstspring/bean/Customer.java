package com.evan.firstspring.bean;


import com.baomidou.mybatisplus.annotation.*;


public class Customer {
    @TableId
    private String customerId;

    private String customerPassword;
    private String customerName;
    private String customerContact;
    private int customerScore;
    private boolean customerTag;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public int getCustomerScore() {
        return customerScore;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public boolean getCustomerTag() {
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

    public void setCustomerTag(boolean customerTag) {
        this.customerTag = customerTag;
    }
}
