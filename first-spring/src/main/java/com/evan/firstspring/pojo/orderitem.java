package com.evan.firstspring.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "orderitem")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class orderitem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    int orderItemId;
    @Column(name = "order_item_orders_id")
    int orderItemOrdersId;
    @Column(name = "order_item_product_id")
    int orderItemProductId;
    @Column(name = "order_item_product_quantity")
    int orderItemProductQuantity;
    @Column(name = "order_item_product_price")
    int orderItemProductPrice;

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

    public int getOrderItemProductPrice() {
        return orderItemProductPrice;
    }

    public void setOrderItemProductPrice(int orderItemProductPrice) {
        this.orderItemProductPrice = orderItemProductPrice;
    }

    public int getOrderItemProductQuantity() {
        return orderItemProductQuantity;
    }

    public void setOrderItemProductQuantity(int orderItemProductQuantity) {
        this.orderItemProductQuantity = orderItemProductQuantity;
    }
}
