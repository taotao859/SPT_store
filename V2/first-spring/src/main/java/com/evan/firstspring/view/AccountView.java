package com.evan.firstspring.view;

import com.evan.firstspring.bean.Account;
import com.evan.firstspring.bean.Staff;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountView {
    private int index;
    private int orderId;
    private Timestamp orderTime;
    private String orderCategory;
    private BigDecimal orderProfit;


    public AccountView(int index, int orderId, Timestamp orderTime, String orderCategory, BigDecimal orderProfit) {
        this.index = index;
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderCategory = orderCategory;
        this.orderProfit = orderProfit;
    }

    public static List<AccountView> getAccountView(List<Account> accountList){
        List<AccountView> accountViewList=new ArrayList<>();

        for(int i=0;i<accountList.size();i++)
        {
            Account account=accountList.get(i);
            String Tag;
            if(account.isAccountTag()){
                Tag = "批发订单";
            }else {
                Tag = "零售订单";
            }
            accountViewList.add(new AccountView(i, account.getAccountIndex(), account.getAccountTime(), Tag, account.getAccountAmount()));
        }

        return accountViewList;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(String orderCategory) {
        this.orderCategory = orderCategory;
    }

    public BigDecimal getOrderProfit() {
        return orderProfit;
    }

    public void setOrderProfit(BigDecimal orderProfit) {
        this.orderProfit = orderProfit;
    }
}
