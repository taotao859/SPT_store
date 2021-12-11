package com.evan.firstspring.view;

import com.evan.firstspring.bean.Product;
import com.evan.firstspring.bean.Purchase;
import com.evan.firstspring.mapper.ProductMapper;
import com.evan.firstspring.mapper.RepositoryMapper;
import com.evan.firstspring.mapper.StaffMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PurchaseView {
    private int index;
    private int productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
    private String operator;
    private String time;
    private String repository;

    public static List<PurchaseView> getPurchaseViewList(List<Purchase> purchaseList, StaffMapper staffMapper, ProductMapper productMapper, RepositoryMapper repositoryMapper) {
        List<PurchaseView> purchaseViewList = new ArrayList<>();

        for (int i = 0; i < purchaseList.size(); i++) {
            Purchase purchase=purchaseList.get(i);
            Product product=productMapper.selectById(purchase.getPurchaseProductId());
            purchaseViewList.add(new PurchaseView(i,product.getProductId(),product.getProductName(),purchase.getPurchaseQuantity(),purchase.getPurchasePrice(),staffMapper.selectById(purchase.getPurchaseStaffId()).getStaffName(),purchase.getPurchaseTime().toString(),repositoryMapper.selectById(purchase.getPurchaseRepositoryId()).getRepositoryName()));
        }

        return purchaseViewList;
    }

    public PurchaseView(int index, int productId, String productName, int quantity, BigDecimal price, String operator,String time,String repository) {
        this.index = index;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.operator = operator;
        this.time=time;
        this.repository=repository;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
