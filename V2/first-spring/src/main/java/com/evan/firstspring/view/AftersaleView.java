package com.evan.firstspring.view;

import com.evan.firstspring.bean.Aftersale;
import com.evan.firstspring.bean.Orders;
import com.evan.firstspring.mapper.OrdersMapper;
import com.evan.firstspring.mapper.StaffMapper;

import java.util.ArrayList;
import java.util.List;

public class AftersaleView {
    private int index;
    private int ordersId;
    private String ordersTime;
    private String operator;

    public static List<AftersaleView> getAftersaleView(List<Aftersale> aftersaleList, OrdersMapper ordersMapper, StaffMapper staffMapper)
    {
        List<AftersaleView> aftersaleViewsList=new ArrayList<>();
        for(int i=0;i<aftersaleList.size();i++)
        {
            Aftersale aftersale=aftersaleList.get(i);
            Orders orders=ordersMapper.selectById(aftersale.getAfterSaleOrdersId());
            aftersaleViewsList.add(new AftersaleView(i,orders.getOrdersId(),orders.getOrdersTime().toString(),staffMapper.selectById(orders.getOrdersStaffId()).getStaffName()));
        }
        return aftersaleViewsList;
    }

    public AftersaleView(int index, int ordersId, String ordersTime, String operator) {
        this.index = index;
        this.ordersId = ordersId;
        this.ordersTime = ordersTime;
        this.operator = operator;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    public String getOrdersTime() {
        return ordersTime;
    }

    public void setOrdersTime(String ordersTime) {
        this.ordersTime = ordersTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
