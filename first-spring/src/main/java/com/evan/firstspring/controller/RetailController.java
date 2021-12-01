package com.evan.firstspring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.evan.firstspring.bean.*;
import com.evan.firstspring.mapper.*;
import com.evan.firstspring.result.Result;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    /**
     * 初始化订单
     *
     * @param staffId    员工ID
     * @param customerId 客户ID
     * @return 初始化成功，则返回该订单，否则返回Null
     */
    @PostMapping("init")
    public Orders Init(@RequestParam String staffId, @RequestParam int repositoryId, @RequestParam(required = false) String customerId) {
        Orders orders = new Orders();
        orders.setOrdersStaffId(staffId);
        if (customerId != null) {
            orders.setOrdersCustomerId(customerId);
        }
        orders.setOrdersTime(new DateTime());
        orders.setOrdersTotalPrice(BigDecimal.valueOf(0));
        orders.setOrdersProfit(BigDecimal.valueOf(0));
        orders.setOrdersRepositoryId(repositoryId);
        orders.setOrdersState(0); //默认状态为未审核
        orders.setOrdersTag(false);//0零售
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
     * @return 一个包含两个元素的map，第一个元素的key为“订单”，value为orderitemList;第二个元素的key为“总价”，value为totalPrice
     */
    @PostMapping("add")
    public Map<String, Object> addOrderitem(@RequestParam int productId, @RequestParam int productQuantity, @RequestBody Orders orders) {
        QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
        orderitemQueryWrapper.eq("order_item_orders_id", orders.getOrdersId());
        List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);
        List<BigDecimal> totalPriceList = new ArrayList<>(); //总价
        Map<String, Object> map = new HashMap<>(); //第一个map元素是订单，第二个map元素是总价

        BigDecimal productRetailPrice = productMapper.selectById(productId).getProductRetailPrice();
        orders.setOrdersTotalPrice(orders.getOrdersTotalPrice().add(productRetailPrice.multiply(BigDecimal.valueOf(productQuantity))));
        ordersMapper.updateById(orders);
        // 在OrderItemList中找到对应商品
        for (Orderitem orderitem : orderitemList) {
            if (orderitem.getOrderItemProductId() == productId) {
                orderitem.setOrderItemProductQuantity(orderitem.getOrderItemProductQuantity() + productQuantity);
                orderitemMapper.updateById(orderitem);
                //计算总价
                for(Orderitem orderitem1:orderitemList){
                    totalPriceList.add(orderitem1.getOrderItemProductPrice().multiply(BigDecimal.valueOf(orderitem1.getOrderItemProductQuantity())));
                }
                map.put("orderitemList", orderitemList);
                map.put("totalPriceList",totalPriceList);
                map.put("ordersTotalPrice", orders.getOrdersTotalPrice());
                return map;
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
        //计算总价
        for(Orderitem orderitem1:orderitemList){
            totalPriceList.add(orderitem1.getOrderItemProductPrice().multiply(BigDecimal.valueOf(orderitem1.getOrderItemProductQuantity())));
        }
		map.put("orderitemList", orderitemList);
        map.put("totalPriceList",totalPriceList);
        map.put("ordersTotalPrice", orders.getOrdersTotalPrice());
        return map;
    }


    // 客户付款
    @PostMapping("pay")
    public Result payOrder(@RequestBody Orders orders){
        orders.setOrdersState(3); // 更新状态为已付款
        ordersMapper.updateById(orders); //更新数据库订单
        QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
        orderitemQueryWrapper.eq("order_item_orders_id", orders.getOrdersId());
        List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);

        // 计算单个Order的利润
        for (Orderitem orderitem : orderitemList){
            Product product = productMapper.selectById(orderitem.getOrderItemProductId());
            BigDecimal productProfit = product.getProductRetailPrice().subtract(product.getProductCost());
            orders.setOrdersProfit(orders.getOrdersProfit().add(productProfit.multiply(BigDecimal.valueOf(orderitem.getOrderItemProductQuantity()))));
        }

        // record记录增加和库存改变, 之后修改中间的第一个1为仓库名
        for (Orderitem orderitem : orderitemList){
            Record record = new Record();
            record.setRecordProductId(orderitem.getOrderItemProductId());
            record.setRecordQuantity(orderitem.getOrderItemProductQuantity());
            record.setRecordTime(new DateTime());
            record.setRecordRepositoryId(orders.getOrdersRepositoryId());
            recordMapper.insert(record);
            RecordController recordController = new RecordController();
            recordController.outInventory(orderitem.getOrderItemProductId(), orderitem.getOrderItemProductQuantity(), orders.getOrdersRepositoryId());
        }

        // 收支表变化
        Account account = new Account();
        account.setAccountAmount(orders.getOrdersTotalPrice());
        account.setAccountTime(new DateTime());
        account.setAccountIndex(orders.getOrdersId());
        account.setAccountTag(false); // 零售是false
        accountMapper.insert(account);

        // 返回收款成功信息
        return new Result(200);
    }


    // 删除单个货品
    @PostMapping("delete")
    public Map<String, Object> deleteOrderItem(@RequestParam int productId, @RequestParam int productQuantity, @RequestBody Orders orders){
        QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
        orderitemQueryWrapper.eq("order_item_orders_id", orders.getOrdersId());
        List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);
        List<BigDecimal> totalPriceList = new ArrayList<>();//总价
        Boolean isDelete = true;
        Map<String, Object> map = new HashMap<>(); //第一个map元素是订单，第二个map元素是总价

        // 在OrderItemList中找到对应商品
        for (Orderitem orderitem : orderitemList) {
            if (orderitem.getOrderItemProductId() == productId) {
                int numberLeft = orderitem.getOrderItemProductQuantity() - productQuantity;
                BigDecimal productRetailPrice = productMapper.selectById(productId).getProductRetailPrice();
                if(numberLeft > 0){
                    orders.setOrdersTotalPrice(orders.getOrdersTotalPrice().subtract(productRetailPrice.multiply(BigDecimal.valueOf(productQuantity))));
                    ordersMapper.updateById(orders);
                    orderitem.setOrderItemProductQuantity(numberLeft);
                    orderitemMapper.updateById(orderitem);
                }
                else{
                    orders.setOrdersTotalPrice(orders.getOrdersTotalPrice().subtract(productRetailPrice.multiply(BigDecimal.valueOf(orderitem.getOrderItemProductQuantity()))));
                    ordersMapper.updateById(orders);
                    orderitemMapper.deleteById(orderitem.getOrderItemId());
                    orderitemList.remove(orderitem);
                }
                //计算总价
                for(Orderitem orderitem1:orderitemList){
                    totalPriceList.add(orderitem1.getOrderItemProductPrice().multiply(BigDecimal.valueOf(orderitem1.getOrderItemProductQuantity())));
                }
                map.put("orderitemList", orderitemList);
                map.put("totalPriceList",totalPriceList);
                map.put("ordersTotalPrice", orders.getOrdersTotalPrice());
                map.put("isDelete", isDelete);
                return map;
            }
        }
        //计算总价
        for(Orderitem orderitem1:orderitemList){
            totalPriceList.add(orderitem1.getOrderItemProductPrice().multiply(BigDecimal.valueOf(orderitem1.getOrderItemProductQuantity())));
        }
        isDelete = false;
        map.put("orderitemList", orderitemList);
        map.put("totalPriceList",totalPriceList);
        map.put("ordersTotalPrice", orders.getOrdersTotalPrice());
        map.put("isDelete", isDelete);
        return map;
    }

    // 取消订单
    @PostMapping("cancel")
    public Result cancelOrder(@RequestBody Orders orders) {
        QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
        orderitemQueryWrapper.eq("order_item_orders_id", orders.getOrdersId());
        List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);
        // 删除orderitem
        for(Orderitem orderitem : orderitemList){
            orderitemMapper.deleteById(orderitem.getOrderItemId());
        }
        // 删除order
        ordersMapper.deleteById(orders.getOrdersId());
        return new Result(200);
    }

    /**
     *
     * @return 所有零售订单
     */
    @PostMapping("all")
    public List<Orders> findAllRetail(){
        QueryWrapper<Orders> ordersQueryWrapper=new QueryWrapper<>();
        ordersQueryWrapper.eq("ordesTag",false);
        return ordersMapper.selectList(ordersQueryWrapper);
    }
}
