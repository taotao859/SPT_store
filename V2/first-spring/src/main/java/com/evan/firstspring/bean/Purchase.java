package com.evan.firstspring.bean;

import com.baomidou.mybatisplus.annotation.*;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Purchase {
    @TableId(type = IdType.AUTO)
    private int purchaseId;
    private String purchaseStaffId;
    private int purchaseRepositoryId;
    private int purchaseProductId;
    private int purchaseQuantity;
    private BigDecimal purchasePrice;
    private Timestamp purchaseTime;

    public int getPurchaseRepositoryId() {
        return purchaseRepositoryId;
    }

    public void setPurchaseRepositoryId(int purchaseRepositoryId) {
        this.purchaseRepositoryId = purchaseRepositoryId;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getPurchaseStaffId() {
        return purchaseStaffId;
    }

    public void setPurchaseStaffId(String purchaseStaffId) {
        this.purchaseStaffId = purchaseStaffId;
    }

    public int getPurchaseProductId() {
        return purchaseProductId;
    }

    public void setPurchaseProductId(int purchaseProductId) {
        this.purchaseProductId = purchaseProductId;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Timestamp getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Timestamp purchaseTime) {
        this.purchaseTime = purchaseTime;
    }
}
