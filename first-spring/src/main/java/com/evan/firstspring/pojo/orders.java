package com.evan.firstspring.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})

public class orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    int ordersId;
    @Column(name = "orders_customer_id")
    int ordersCustomerId;
    @Column(name = "orders_time")
    String ordersTime;
    @Column(name = "orders_total_price")
    int ordersTotalPrice;
    @Column(name = "orders_profit")
    int ordersProfit;
    @Column(name = "orders_state")
    int ordersState;

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

    public int getOrdersProfit() {
        return ordersProfit;
    }

    public void setOrdersProfit(int ordersProfit) {
        this.ordersProfit = ordersProfit;
    }

    public int getOrdersState() {
        return ordersState;
    }

    public void setOrdersState(int ordersState) {
        this.ordersState = ordersState;
    }

    public int getOrdersTotalPrice() {
        return ordersTotalPrice;
    }

    public void setOrdersTime(String ordersTime) {
        this.ordersTime = ordersTime;
    }

    public String getOrdersTime() {
        return ordersTime;
    }

    public void setOrdersTotalPrice(int ordersTotalPrice) {
        this.ordersTotalPrice = ordersTotalPrice;
    }
}
