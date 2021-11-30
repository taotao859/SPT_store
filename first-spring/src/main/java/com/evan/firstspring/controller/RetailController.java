package com.evan.firstspring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.Orderitem;
import com.evan.firstspring.bean.Orders;
import com.evan.firstspring.mapper.*;
import com.evan.firstspring.result.Result;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/retail")
@CrossOrigin
public class RetailController {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderitemMapper orderitemMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 初始化订单
     *
     * @param staffId    员工ID
     * @param customerId 客户ID
     * @return 初始化成功，则返回该订单，否则返回Null
     */
    @PostMapping("init")
    public Orders Init(@RequestParam String staffId, @RequestParam(required = false) String customerId) {
        Orders orders = new Orders();
        orders.setOrdersStaffId(staffId);
        if (customerId != null) {
            orders.setOrdersCustomerId(customerId);
        }
        orders.setOrdersTime(new DateTime());
        orders.setOrdersTotalPrice(BigDecimal.valueOf(0));
        orders.setOrdersProfit(BigDecimal.valueOf(0));
        orders.setOrdersState(0);
        int i = ordersMapper.insert(orders);
        if (i > 0) {
            return orders;
        } else {
            return null;
        }
    }

    /**
     * 向订单添加新项
     *
     * @param productId       新添的订单表项货品ID
     * @param productQuantity 新添的订单表项货品数量
     * @return 状态码，成功：200，失败：400
     */
    @PostMapping("add")
    public List<Orderitem> addOrderitem(@RequestParam int productId, @RequestParam int productQuantity, @RequestBody Orders orders) {
        QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
        orderitemQueryWrapper.eq("order_item_orders_id", orders.getOrdersId());
        List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);

        BigDecimal productRetailPrice = productMapper.selectById(productId).getProductRetailPrice();
        orders.setOrdersTotalPrice(orders.getOrdersTotalPrice().add(productRetailPrice.multiply(BigDecimal.valueOf(productQuantity))));
        ordersMapper.updateById(orders);
        // 在OrderItemList中找到对应商品
        for (Orderitem orderitem : orderitemList) {
            if (orderitem.getOrderItemProductId() == productId) {
                orderitem.setOrderItemProductQuantity(orderitem.getOrderItemProductQuantity() + productQuantity);
                orderitemMapper.updateById(orderitem);
                return orderitemList;
            }
        }
        // 在OrderItemList中未找到对应商品
        Orderitem orderitem = new Orderitem();
        orderitem.setOrderItemProductId(productId);
        orderitem.setOrderItemProductQuantity(productQuantity);
        orderitem.setOrderItemOrdersId(orders.getOrdersId());
        orderitem.setOrderItemProductPrice(productRetailPrice);

        orderitemMapper.insert(orderitem);
        orderitemList.add(orderitem);
        return orderitemList;
    }


    //计算利润，并且零售必须收款,记住改库存（先Record,然后由record影响到inventory）
//    @PostMapping("calculate")
//    private Result calculateProfit(@RequestBody Orders orders) {
//        QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
//        orderitemQueryWrapper.eq("order_item_orders_id", orders.getOrdersId());
//        List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);
//
//        if () {
//
//        }
//    }
}
