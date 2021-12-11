package com.evan.firstspring.view;

import com.evan.firstspring.bean.Pandian;
import com.evan.firstspring.bean.Product;
import com.evan.firstspring.bean.Repository;
import com.evan.firstspring.mapper.ProductMapper;
import com.evan.firstspring.mapper.RepositoryMapper;

import java.util.ArrayList;
import java.util.List;

public class PandianView {
    private int index;
    private int productId;
    private String productName;
    private String repositoryName;
    private int updateQuantity;

    public PandianView(int index, int productId, String productName, String repositoryName, int updateQuantity) {
        this.index = index;
        this.productId = productId;
        this.productName = productName;
        this.repositoryName = repositoryName;
        this.updateQuantity = updateQuantity;
    }

    public static List<PandianView> getPandianView(List<Pandian> pandianList, ProductMapper productMapper, RepositoryMapper repositoryMapper){
        List<PandianView> pandianViewList = new ArrayList<>();
        for(int i = 0; i < pandianList.size(); i++){
            Pandian pandian = pandianList.get(i);
            Product product = productMapper.selectById(pandian.getPandianProductId());
            System.out.println(product);
            Repository repository = repositoryMapper.selectById(pandian.getPandianRepositoryId());
            System.out.println(repository);
            pandianViewList.add(new PandianView(i, product.getProductId(), product.getProductName(), repository.getRepositoryName(),pandian.getPandianChangeQuantity()));
        }
        return pandianViewList;
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

    public int getUpdateQuantity() {
        return updateQuantity;
    }

    public void setUpdateQuantity(int updateQuantity) {
        this.updateQuantity = updateQuantity;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }
}
