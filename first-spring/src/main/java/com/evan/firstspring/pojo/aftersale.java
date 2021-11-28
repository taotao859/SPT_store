package com.evan.firstspring.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "aftersale")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})

public class aftersale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "after_sale_id")
    int afterSaleId;
    @Column(name = "after_sale_orders_id")
    int afterSaleOrdersId;

    public int getAfterSaleId() {
        return afterSaleId;
    }

    public void setAfterSaleId(int afterSaleId) {
        this.afterSaleId = afterSaleId;
    }

    public int getAfterSaleOrdersId() {
        return afterSaleOrdersId;
    }

    public void setAfterSaleOrdersId(int afterSaleOrdersId) {
        this.afterSaleOrdersId = afterSaleOrdersId;
    }
}
