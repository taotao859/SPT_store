package com.evan.firstspring.bean;

import com.baomidou.mybatisplus.annotation.*;
import org.joda.time.DateTime;

import java.math.BigDecimal;

public class Orders {
    @TableId(type = IdType.AUTO)
    private int ordersId;
    private int ordersCustomerId;
    private DateTime ordersTime;
    private BigDecimal ordersTotalPrice;
    private BigDecimal ordersProfit;
    private int ordersState;

    public int getOrdersCustomerId() {
        return ordersCustomerId;
    }

    public void setOrdersCustomerId(int ordersCustomerId) {
        this.ordersCustomerId = ordersCustomerId;
    }

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    public BigDecimal getOrdersProfit() {
        return ordersProfit;
    }

    public void setOrdersProfit(BigDecimal ordersProfit) {
        this.ordersProfit = ordersProfit;
    }

    public int getOrdersState() {
        return ordersState;
    }

    public void setOrdersState(int ordersState) {
        this.ordersState = ordersState;
    }

    public BigDecimal getOrdersTotalPrice() {
        return ordersTotalPrice;
    }

    public void setOrdersTime(DateTime ordersTime) {
        this.ordersTime = ordersTime;
    }

    public DateTime getOrdersTime() {
        return ordersTime;
    }

    public void setOrdersTotalPrice(BigDecimal ordersTotalPrice) {
        this.ordersTotalPrice = ordersTotalPrice;
    }
}
