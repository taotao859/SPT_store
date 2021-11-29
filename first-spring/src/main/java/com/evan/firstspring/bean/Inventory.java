package com.evan.firstspring.bean;

import com.baomidou.mybatisplus.annotation.*;

public class Inventory {
    @TableId(type = IdType.AUTO)
    private int inventoryId;
    private int inventoryProductId;
    private int inventoryQuantity;
    private String inventoryRepository;

    public String getInventoryRepository() {
        return inventoryRepository;
    }

    public void setInventoryRepository(String inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

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
