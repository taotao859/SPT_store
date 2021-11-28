package com.evan.firstspring.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "inventory")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})

public class inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    int inventoryId;
    @Column(name = "inventory_product_id")
    int inventoryProductId;
    @Column(name = "inventory_quantity")
    int inventoryQuantity;

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getInventoryProductId() {
        return inventoryProductId;
    }

    public void setInventoryProductId(int inventoryProductId) {
        this.inventoryProductId = inventoryProductId;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }
}
