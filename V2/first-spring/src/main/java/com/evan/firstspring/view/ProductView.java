package com.evan.firstspring.view;

import com.evan.firstspring.bean.Product;
import com.evan.firstspring.mapper.ProductMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductView {
    private int index;
    private int productId;
    private String productName;
    private BigDecimal productCost;
    private BigDecimal retailPrice;

    public static List<ProductView> getProductViewList(List<Product> productList, ProductMapper productMapper)
    {
        List<ProductView> productViewList=new ArrayList<>();
        for(int i=0;i<productList.size();i++)
        {
            Product product=productList.get(i);
            productViewList.add(new ProductView(i,product.getProductId(),productMapper.selectById(product.getProductId()).getProductName(),product.getProductCost(),product.getProductRetailPrice()));
        }
        return productViewList;
    }

    public ProductView(int index, int productId, String productName, BigDecimal productCost, BigDecimal retailPrice) {
        this.index = index;
        this.productId = productId;
        this.productName = productName;
        this.productCost = productCost;
        this.retailPrice = retailPrice;
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

    public BigDecimal getProductCost() {
        return productCost;
    }

    public void setProductCost(BigDecimal productCost) {
        this.productCost = productCost;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }
}
