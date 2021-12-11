package com.evan.firstspring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.Inventory;
import com.evan.firstspring.bean.Orderitem;
import com.evan.firstspring.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class RecordController {
    public InventoryMapper getInventoryMapper() {
        return inventoryMapper;
    }

    public void setInventoryMapper(InventoryMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }

    private InventoryMapper inventoryMapper;

    public RecordController(InventoryMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }

    /**
     * 出库
     * @param productId 货品ID
     * @param productQuantity 货品数量
     * @param repositoryId 仓库Id
     * @return true代表成功出库，false代表出库失败
     */
    public boolean outInventory(int productId, int productQuantity, int repositoryId) {
        QueryWrapper<Inventory> inventoryQueryWrapper = new QueryWrapper<>();
        inventoryQueryWrapper.eq("inventory_repository_id", repositoryId);
        inventoryQueryWrapper.eq("inventory_product_id", productId);
        List<Inventory> inventoryList = inventoryMapper.selectList(inventoryQueryWrapper);

        if(inventoryList.size() == 1){
            Inventory inventory = inventoryList.get(0);
            int inventoryQuantity = inventory.getInventoryQuantity();
            int inventoryLeft = inventoryQuantity - productQuantity;
            // 库存不够
            if(inventoryLeft < 0){
                return false;
            }
            // 库存有余
            else if(inventoryLeft > 0){
                inventory.setInventoryQuantity(inventoryLeft);
                inventoryMapper.updateById(inventory);
                return true;
            }
            // 库存刚好用光
            else {
                inventoryMapper.deleteById(inventory.getInventoryId());
                return true;
            }
        }
        return false;
    }

    /**
     * 向仓库里加入quantity个对应Id的货品
     * @param productId 货品ID
     * @param productQuantity 货品数量
     * @param repositoryId 仓库ID
     * @return true代表插入成功，false代表插入失败
     */
    public boolean inInventory(int productId, int productQuantity, int repositoryId) {
        if(productQuantity <= 0) return false;
        QueryWrapper<Inventory> inventoryQueryWrapper = new QueryWrapper<>();
        inventoryQueryWrapper.eq("inventory_repository_id", repositoryId);
        inventoryQueryWrapper.eq("inventory_product_id", productId);
        List<Inventory> inventoryList = inventoryMapper.selectList(inventoryQueryWrapper);
        if(inventoryList.size() == 1){
            Inventory inventory = inventoryList.get(0);
            int inventoryQuantity = inventory.getInventoryQuantity();
            int inventoryLeft = inventoryQuantity + productQuantity;
            inventory.setInventoryQuantity(inventoryLeft);
            inventoryMapper.updateById(inventory);
            return true;
        }
        else {
            Inventory inventory = new Inventory();
            inventory.setInventoryProductId(productId);
            inventory.setInventoryQuantity(productQuantity);
            inventory.setInventoryRepositoryId(repositoryId);
            int i = inventoryMapper.insert(inventory);
            if(i > 0) return true;
            else return false;
        }
    }

    /**
     * 计算总库存是否有productQuantity
     * @param productId 货品ID
     * @param productQuantity 货品需要的数量
     * @return 不够返回false，够返回true
     */
    public boolean GetAllInventoryNumber(int productId, int productQuantity){
        QueryWrapper<Inventory> inventoryQueryWrapper = new QueryWrapper<>();
        inventoryQueryWrapper.eq("inventory_product_id", productId);
        List<Inventory> inventoryList = inventoryMapper.selectList(inventoryQueryWrapper);
        int number = 0;
        for(Inventory inventory:inventoryList){
            number += inventory.getInventoryQuantity();
        }
        if(number >= productQuantity) return true;
        else return false;
    }


    /**
     * 计算这个仓库是否有productQuantity个
     * @param productId 货品ID
     * @param productQuantity 货品需要数量
     * @param repositoryId 仓库ID
     * @return 不够返回false，够返回true
     */
    public boolean GetThisInventoryNumber(int productId, int productQuantity, int repositoryId){
        QueryWrapper<Inventory> inventoryQueryWrapper = new QueryWrapper<>();
        inventoryQueryWrapper.eq("inventory_repository_id", repositoryId);
        inventoryQueryWrapper.eq("inventory_product_id", productId);

        System.out.println(inventoryMapper);

        List<Inventory> inventoryList = inventoryMapper.selectList(inventoryQueryWrapper);
        if(inventoryList.size() == 1){
            Inventory inventory = inventoryList.get(0);
            int inventoryQuantity = inventory.getInventoryQuantity();
            int inventoryLeft = inventoryQuantity - productQuantity;
            // 库存不够
            // 库存有余
            return inventoryLeft >= 0;
        }
        return false;
    }
}
