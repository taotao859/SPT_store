package com.evan.firstspring.bean;

import com.baomidou.mybatisplus.annotation.*;

public class Inventory {
    @TableId(type = IdType.AUTO)
    private int inventoryId;
    private int inventoryProductId;
    private int inventoryQuantity;
    private int inventoryRepositoryId;

    public int getInventoryRepositoryId() {
        return inventoryRepositoryId;
    }

    public void setInventoryRepositoryId(int inventoryRepositoryId) {
        this.inventoryRepositoryId = inventoryRepositoryId;
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
