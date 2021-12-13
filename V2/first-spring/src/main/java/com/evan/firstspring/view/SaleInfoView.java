package com.evan.firstspring.view;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.Customer;
import com.evan.firstspring.bean.Orderitem;
import com.evan.firstspring.bean.Orders;
import com.evan.firstspring.bean.Product;
import com.evan.firstspring.mapper.CustomerMapper;
import com.evan.firstspring.mapper.OrderitemMapper;
import com.evan.firstspring.mapper.OrdersMapper;
import com.evan.firstspring.mapper.ProductMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaleInfoView {
    private int index;
    private int orderId;
    private String productName;
    private String customerName;
    private String orderState;
    private BigDecimal orderPrice;

    public SaleInfoView(int index, int orderId, String productName, String customerName, String orderState, BigDecimal orderPrice) {
        this.index = index;
        this.orderId = orderId;
        this.productName = productName;
        this.customerName = customerName;
        this.orderState = orderState;
        this.orderPrice = orderPrice;
    }

    public static List<SaleInfoView> getSaleInfoViewList(String productName, String customerName, OrdersMapper ordersMapper, OrderitemMapper orderitemMapper, ProductMapper productMapper, CustomerMapper customerMapper){
        List<SaleInfoView> saleInfoViewList = new ArrayList<>();
        List<Orders> ordersList = ordersMapper.selectList(null);
        List<String> statusList = Arrays.asList("未审核", "审核未通过", "待付款", "已付款", "退货");
        int i = 0;
        // 对所有的orders进行遍历搜索
        for (Orders orders:ordersList){
            // 对客户名字进行搜索
            Customer customer = customerMapper.selectById(orders.getOrdersCustomerId());
            String customerInsName;
            if (customer == null) {
                customerInsName = "未注册的客户";
            }
            else customerInsName = customer.getCustomerName();
            // 如果有所输入且匹配不上，找下一个
            if (customerName != null && !customerName.equals("") && !customerName.equals(customerInsName)){
                continue;
            }
            QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
            orderitemQueryWrapper.eq("order_item_orders_id", orders.getOrdersId());
            List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);
            for(Orderitem orderitem:orderitemList){
                Product product = productMapper.selectById(orderitem.getOrderItemProductId());
                // 对应的货品名有所输入，但是匹配不上
                if (productName!=null && !productName.equals("") && !productName.equals(product.getProductName())){
                    continue;
                }
                BigDecimal orderPrice = orderitem.getOrderItemProductPrice().multiply(BigDecimal.valueOf(orderitem.getOrderItemProductQuantity()));
                saleInfoViewList.add(new SaleInfoView(i, orders.getOrdersId(), product.getProductName(), customerInsName, statusList.get(orders.getOrdersState()), orderPrice));
                i += 1;
            }
        }
        return saleInfoViewList;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }
}
