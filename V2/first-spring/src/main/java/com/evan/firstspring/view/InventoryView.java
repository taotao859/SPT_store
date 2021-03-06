package com.evan.firstspring.view;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.Inventory;
import com.evan.firstspring.bean.Product;
import com.evan.firstspring.bean.Repository;
import com.evan.firstspring.mapper.InventoryMapper;
import com.evan.firstspring.mapper.ProductMapper;
import com.evan.firstspring.mapper.RepositoryMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InventoryView {
    private int index;
    private int productId;
    private String productName;
    private int quantity;
    private String repository;
    private BigDecimal productStockMoney;

    public static List<InventoryView> getInventoryView(List<Inventory> inventoryList, ProductMapper productMapper, RepositoryMapper repositoryMapper, InventoryMapper inventoryMapper){
        List<InventoryView> inventoryViewList=new ArrayList<>();
        for (int i=0;i<inventoryList.size();i++){
            Inventory inventory=inventoryList.get(i);
            Product product=productMapper.selectById(inventory.getInventoryProductId());
            inventoryViewList.add(new InventoryView(i,product.getProductId(),product.getProductName(),inventory.getInventoryQuantity(),repositoryMapper.selectById(inventory.getInventoryRepositoryId()).getRepositoryName(),product.getProductCost().multiply(BigDecimal.valueOf(inventory.getInventoryQuantity()))));
        }
        return inventoryViewList;
    }

    public InventoryView(int index, int productId, String productName, int quantity, String repository,BigDecimal productStockMoney) {
        this.index = index;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.repository = repository;
        this.productStockMoney = productStockMoney;
    }

    public BigDecimal getProductStockMoney() {
        return productStockMoney;
    }

    public void setProductStockMoney(BigDecimal productStockMoney) {
        this.productStockMoney = productStockMoney;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }
}
