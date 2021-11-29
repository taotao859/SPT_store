package com.evan.firstspring.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;


public class Product {
    @TableId(type = IdType.AUTO)
    private int productId;
    private String productName;
    private BigDecimal productCost;

    public BigDecimal getProductCost() {
        return productCost;
    }

    public void setProductCost(BigDecimal productCost) {
        this.productCost = productCost;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
