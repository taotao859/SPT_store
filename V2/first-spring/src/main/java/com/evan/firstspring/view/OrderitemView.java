package com.evan.firstspring.view;

import java.math.BigDecimal;

public class OrderitemView {
    String Id;
    int productId;
    String productName;
    int productQuantity;
    BigDecimal productRetailPrice;
    BigDecimal singlePrice;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public BigDecimal getProductRetailPrice() {
        return productRetailPrice;
    }

    public void setProductRetailPrice(BigDecimal productRetailPrice) {
        this.productRetailPrice = productRetailPrice;
    }

    public BigDecimal getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(BigDecimal singlePrice) {
        this.singlePrice = singlePrice;
    }

    public void calculatePrice(){
        this.singlePrice=this.productRetailPrice.multiply(BigDecimal.valueOf(this.productQuantity));
    }
}
