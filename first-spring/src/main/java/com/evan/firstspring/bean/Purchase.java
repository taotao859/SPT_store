package com.evan.firstspring.bean;

import com.baomidou.mybatisplus.annotation.*;
import org.joda.time.DateTime;

import java.math.BigDecimal;

public class Purchase {
    @TableId(type = IdType.AUTO)
    private int purchaseId;
    private String purchaseStaffId;
    private String purchaseRepository;
    private int purchaseProductId;
    private int purchaseQuantity;
    private BigDecimal purchasePrice;
    private DateTime purchaseDate;

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

    public String getPurchaseRepository() {
        return purchaseRepository;
    }

    public void setPurchaseRepository(String purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
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

    public DateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(DateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
