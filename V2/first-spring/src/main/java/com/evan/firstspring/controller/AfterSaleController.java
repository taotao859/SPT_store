package com.evan.firstspring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.*;
import com.evan.firstspring.mapper.*;
import com.evan.firstspring.result.Result;
import com.evan.firstspring.view.AftersaleView;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/aftersale")
@CrossOrigin
public class AfterSaleController {
    @Autowired
    private AftersaleMapper aftersaleMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderitemMapper orderitemMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private InventoryMapper inventoryMapper;
    @Autowired
    private StaffMapper staffMapper;


    /**
     * 增加一条退货订单
     * @param orders 传入的订单
     * @return 状态码，200表示退货成功，400表示退货失败
     */
    @PostMapping("add")
    public Result addAfterSale(@RequestBody Orders orders) {
        // 改变orders的状态
        orders.setOrdersState(4); //设置订单状态为4：退货
        ordersMapper.updateById(orders);
        // 改变库存，将货品退至订单对应的仓库
        QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
        orderitemQueryWrapper.eq("order_item_orders_id", orders.getOrdersId());
        List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);
        for(Orderitem orderitem:orderitemList){
            Record record = new Record();
            record.setRecordProductId(orderitem.getOrderItemProductId());
            record.setRecordQuantity(orderitem.getOrderItemProductQuantity());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            record.setRecordTime(timestamp);
            record.setRecordRepositoryId(orders.getOrdersRepositoryId());
            recordMapper.insert(record);
            RecordController recordController = new RecordController(inventoryMapper);
            recordController.inInventory(orderitem.getOrderItemProductId(), orderitem.getOrderItemProductQuantity(), orders.getOrdersRepositoryId());
        }
        // 改变收支表
        Account account = new Account();
        account.setAccountAmount(orders.getOrdersTotalPrice().negate()); //收支表插入一个负的款项，即退款
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        account.setAccountTime(timestamp);
        account.setAccountIndex(orders.getOrdersId());
        account.setAccountTag(false); // 当款项是负时，退货是false
        accountMapper.insert(account);
        // 增加一条售后记录
        Aftersale aftersale = new Aftersale();
        aftersale.setAfterSaleOrdersId(orders.getOrdersId());
        aftersaleMapper.insert(aftersale);
        return new Result(200);
    }


    /**
     * 返回退款的所有订单列表
     * @return 退款的所有订单列表
     */
    @GetMapping("all")
    public List<AftersaleView> findAllAfterSale(){
        List<Aftersale> aftersaleList = aftersaleMapper.selectList(null);
        return AftersaleView.getAftersaleView(aftersaleList,ordersMapper,staffMapper);
    }
}
