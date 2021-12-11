package com.evan.firstspring.view;

import com.evan.firstspring.bean.Orders;
import com.evan.firstspring.bean.Product;
import com.evan.firstspring.bean.Record;
import com.evan.firstspring.mapper.*;

import java.util.ArrayList;
import java.util.List;

public class RecordView {
    private int index;
    private String productName;
    private int quantity;
    private String repoName;
    private String time;

    public static List<RecordView> getRecordViewList(List<Record> recordList, ProductMapper productMapper, RepositoryMapper repositoryMapper) {
        List<RecordView> recordViewList = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            Record record=recordList.get(i);
            Product product=productMapper.selectById(record.getRecordProductId());
            recordViewList.add(new RecordView(i,product.getProductName(),record.getRecordQuantity(),repositoryMapper.selectById(record.getRecordRepositoryId()).getRepositoryName(),record.getRecordTime().toString()));
        }
        return recordViewList;
    }

    public RecordView(int index, String productName, int quantity, String repoName, String time) {
        this.index = index;
        this.productName = productName;
        this.quantity = quantity;
        this.repoName = repoName;
        this.time = time;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
