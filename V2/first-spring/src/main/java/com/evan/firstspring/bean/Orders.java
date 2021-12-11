package com.evan.firstspring.bean;

import com.baomidou.mybatisplus.annotation.*;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Orders {
    @TableId(type = IdType.AUTO)
    private int ordersId;
    private String ordersCustomerId;

    private Timestamp ordersTime;
    private BigDecimal ordersTotalPrice;
    private BigDecimal ordersProfit;
    private int ordersState;
    private String ordersStaffId;
    private int ordersRepositoryId;
    private boolean ordersTag;

    public boolean isOrdersTag() {
        return ordersTag;
    }

    public void setOrdersTag(boolean ordersTag) {
        this.ordersTag = ordersTag;
    }

    public int getOrdersRepositoryId() {
        return ordersRepositoryId;
    }

    public void setOrdersRepositoryId(int ordersRepositoryId) {
        this.ordersRepositoryId = ordersRepositoryId;
    }



    public String getOrdersStaffId() {
        return ordersStaffId;
    }

    public void setOrdersStaffId(String ordersStaffId) {
        this.ordersStaffId = ordersStaffId;
    }

    public String getOrdersCustomerId() {
        return ordersCustomerId;
    }

    public void setOrdersCustomerId(String ordersCustomerId) {
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

    public Timestamp getOrdersTime() {
        return ordersTime;
    }

    public void setOrdersTime(Timestamp ordersTime) {
        this.ordersTime = ordersTime;
    }

    public void setOrdersTotalPrice(BigDecimal ordersTotalPrice) {
        this.ordersTotalPrice = ordersTotalPrice;
    }
}
