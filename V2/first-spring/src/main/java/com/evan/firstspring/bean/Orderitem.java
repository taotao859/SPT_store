package com.evan.firstspring.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;

public class Orderitem {
    @TableId(type = IdType.AUTO)
    private int orderItemId;
    private int orderItemOrdersId;
    private int orderItemProductId;
    private int orderItemProductQuantity;
    private BigDecimal orderItemProductPrice;

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderItemOrdersId() {
        return orderItemOrdersId;
    }

    public void setOrderItemOrdersId(int orderItemOrdersId) {
        this.orderItemOrdersId = orderItemOrdersId;
    }

    public int getOrderItemProductId() {
        return orderItemProductId;
    }

    public void setOrderItemProductId(int orderItemProductId) {
        this.orderItemProductId = orderItemProductId;
    }

    public BigDecimal getOrderItemProductPrice() {
        return orderItemProductPrice;
    }

    public void setOrderItemProductPrice(BigDecimal orderItemProductPrice) {
        this.orderItemProductPrice = orderItemProductPrice;
    }

    public int getOrderItemProductQuantity() {
        return orderItemProductQuantity;
    }

    public void setOrderItemProductQuantity(int orderItemProductQuantity) {
        this.orderItemProductQuantity = orderItemProductQuantity;
    }
}
