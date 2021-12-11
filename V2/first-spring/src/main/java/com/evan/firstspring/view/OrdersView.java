package com.evan.firstspring.view;

import com.evan.firstspring.bean.Orders;
import com.evan.firstspring.mapper.StaffMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrdersView {
    private int index;
    private int wholeSaleOrderId;
    private String wholeSaleOrderDate;
    private String wholeSaleOrderOperator;
    private String wholeSaleOrderState;

    public static List<OrdersView> getOrdersViewList(List<Orders> ordersList, StaffMapper staffMapper) {
        List<OrdersView> ordersViewList=new ArrayList<>();
        List<String> statusList = Arrays.asList("未审核", "审核未通过", "待付款", "已付款", "退货");

        for(int i = 0; i< ordersList.size(); i++)
        {
            Orders orders=ordersList.get(i);
            ordersViewList.add(new OrdersView(i,orders.getOrdersId(),orders.getOrdersTime().toString(),staffMapper.selectById(orders.getOrdersStaffId()).getStaffName(),statusList.get(orders.getOrdersState())));
        }

        return ordersViewList;
    }

    public OrdersView(int index, int wholeSaleOrderId, String wholeSaleOrderDate, String wholeSaleOrderOperator, String wholeSaleOrderState) {
        this.index = index;
        this.wholeSaleOrderId = wholeSaleOrderId;
        this.wholeSaleOrderDate = wholeSaleOrderDate;
        this.wholeSaleOrderOperator = wholeSaleOrderOperator;
        this.wholeSaleOrderState = wholeSaleOrderState;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getWholeSaleOrderId() {
        return wholeSaleOrderId;
    }

    public void setWholeSaleOrderId(int wholeSaleOrderId) {
        this.wholeSaleOrderId = wholeSaleOrderId;
    }

    public String getWholeSaleOrderDate() {
        return wholeSaleOrderDate;
    }

    public void setWholeSaleOrderDate(String wholeSaleOrderDate) {
        this.wholeSaleOrderDate = wholeSaleOrderDate;
    }

    public String getWholeSaleOrderOperator() {
        return wholeSaleOrderOperator;
    }

    public void setWholeSaleOrderOperator(String wholeSaleOrderOperator) {
        this.wholeSaleOrderOperator = wholeSaleOrderOperator;
    }

    public String getWholeSaleOrderState() {
        return wholeSaleOrderState;
    }

    public void setWholeSaleOrderState(String wholeSaleOrderState) {
        this.wholeSaleOrderState = wholeSaleOrderState;
    }
}
