package com.evan.firstspring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.*;
import com.evan.firstspring.mapper.*;
import com.evan.firstspring.result.Result;
import com.evan.firstspring.view.OrderitemView;
import com.evan.firstspring.view.OrdersView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
    @Autowired
    private InventoryMapper inventoryMapper;
    @Autowired
    private RepositoryMapper repositoryMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private AftersaleMapper aftersaleMapper;


    /**
     * 初始化订单
     *
     * @param staffId    员工ID
     * @param customerId 客户ID
     * @return 初始化成功，则返回该订单，否则返回Null
     */
    @GetMapping("init")
    public Orders Init(@RequestParam String staffId, @RequestParam int repositoryId, @RequestParam(required = false) String customerId) {
        Orders orders = new Orders();
        orders.setOrdersStaffId(staffId);
        if (customerId != null) {
            orders.setOrdersCustomerId(customerId);
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        orders.setOrdersTime(timestamp);
        orders.setOrdersTotalPrice(BigDecimal.valueOf(0));
        orders.setOrdersProfit(BigDecimal.valueOf(0));
        orders.setOrdersRepositoryId(repositoryId);
        orders.setOrdersState(0); //默认状态为未审核
        orders.setOrdersTag(false);//0零售
        int i = ordersMapper.insert(orders);
        if (i > 0) {
            return orders;
        } else {
            System.out.println("wrong");
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
    @GetMapping("add")
    public Map<String, Object> addOrderitem(@RequestParam int productId, @RequestParam int productQuantity, @RequestParam int ordersId) {
        QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
        orderitemQueryWrapper.eq("order_item_orders_id", ordersId);
        List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);
        Map<String, Object> map = new HashMap<>(); //第一个map元素是订单，第二个map元素是总价

        BigDecimal productRetailPrice = productMapper.selectById(productId).getProductRetailPrice();
        Orders orders = ordersMapper.selectById(ordersId);
        orders.setOrdersTotalPrice(orders.getOrdersTotalPrice().add(productRetailPrice.multiply(BigDecimal.valueOf(productQuantity))));
        ordersMapper.updateById(orders);
        // 在OrderItemList中找到对应商品
        for (Orderitem orderitem : orderitemList) {
            if (orderitem.getOrderItemProductId() == productId) {
                orderitem.setOrderItemProductQuantity(orderitem.getOrderItemProductQuantity() + productQuantity);
                orderitemMapper.updateById(orderitem);

                List<OrderitemView> orderitemViewList = new ArrayList<>();
                int i = 1;
                for(Orderitem orderitem1:orderitemList){
                    OrderitemView orderitemView = new OrderitemView();
                    orderitemView.setId(Integer.toString(i));
                    orderitemView.setProductId(orderitem1.getOrderItemProductId());
                    Product temp_product = productMapper.selectById(orderitem1.getOrderItemProductId());
                    orderitemView.setProductName(temp_product.getProductName());
                    orderitemView.setProductQuantity(orderitem1.getOrderItemProductQuantity());
                    orderitemView.setProductRetailPrice(orderitem1.getOrderItemProductPrice());
                    orderitemView.calculatePrice();
                    orderitemViewList.add(orderitemView);
                    i += 1;
                }
                map.put("orderitemViewList", orderitemViewList);

                map.put("ordersTotalPrice", orders.getOrdersTotalPrice().toString());
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

        List<OrderitemView> orderitemViewList = new ArrayList<>();
        int i = 1;
        for(Orderitem orderitem1:orderitemList){
            OrderitemView orderitemView = new OrderitemView();
            orderitemView.setId(Integer.toString(i));
            orderitemView.setProductId(orderitem1.getOrderItemProductId());
            Product product = productMapper.selectById(orderitem1.getOrderItemProductId());
            orderitemView.setProductName(product.getProductName());
            orderitemView.setProductQuantity(orderitem1.getOrderItemProductQuantity());
            orderitemView.setProductRetailPrice(orderitem1.getOrderItemProductPrice());
            orderitemView.calculatePrice();
            orderitemViewList.add(orderitemView);
            i += 1;
        }
		map.put("orderitemViewList", orderitemViewList);
        map.put("ordersTotalPrice", orders.getOrdersTotalPrice());
        return map;
    }


    // 客户付款
    @PostMapping("pay")
    public Result payOrder(@RequestBody Orders orders){
        Orders orders1 = ordersMapper.selectById(orders.getOrdersId());
        orders1.setOrdersState(3); // 更新状态为已付款
        ordersMapper.updateById(orders1); //更新数据库订单
        QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
        orderitemQueryWrapper.eq("order_item_orders_id", orders1.getOrdersId());
        List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);

        // 计算单个Order的利润
        for (Orderitem orderitem : orderitemList){
            Product product = productMapper.selectById(orderitem.getOrderItemProductId());
            BigDecimal productProfit = product.getProductRetailPrice().subtract(product.getProductCost());
            orders1.setOrdersProfit(orders1.getOrdersProfit().add(productProfit.multiply(BigDecimal.valueOf(orderitem.getOrderItemProductQuantity()))));
        }

        // record记录增加和库存改变, 之后修改中间的第一个1为仓库名
        for (Orderitem orderitem : orderitemList){
            Record record = new Record();
            record.setRecordProductId(orderitem.getOrderItemProductId());
            record.setRecordQuantity(orderitem.getOrderItemProductQuantity());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            record.setRecordTime(timestamp);
            record.setRecordRepositoryId(orders1.getOrdersRepositoryId());
            recordMapper.insert(record);
            RecordController recordController = new RecordController(inventoryMapper);
            recordController.outInventory(orderitem.getOrderItemProductId(), orderitem.getOrderItemProductQuantity(), orders1.getOrdersRepositoryId());
        }

        // 收支表变化
        Account account = new Account();
        account.setAccountAmount(orders1.getOrdersTotalPrice());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        account.setAccountTime(timestamp);
        account.setAccountIndex(orders1.getOrdersId());
        account.setAccountTag(false); // 零售是false
        accountMapper.insert(account);

        // 返回收款成功信息
        return new Result(200);
    }


    // 删除单个货品
    @GetMapping("delete")
    public Map<String, Object> deleteOrderItem(@RequestParam int productId, @RequestParam int productQuantity, @RequestParam int ordersId){
        QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
        orderitemQueryWrapper.eq("order_item_orders_id", ordersId);
        List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);
        Boolean isDelete = true;
        Map<String, Object> map = new HashMap<>(); //第一个map元素是订单，第二个map元素是总价
        Orders orders = ordersMapper.selectById(ordersId);
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

                List<OrderitemView> orderitemViewList = new ArrayList<>();
                int i = 1;
                for(Orderitem orderitem1:orderitemList){
                    OrderitemView orderitemView = new OrderitemView();
                    orderitemView.setId(Integer.toString(i));
                    orderitemView.setProductId(orderitem1.getOrderItemProductId());
                    Product product = productMapper.selectById(orderitem1.getOrderItemProductId());
                    orderitemView.setProductName(product.getProductName());
                    orderitemView.setProductQuantity(orderitem1.getOrderItemProductQuantity());
                    orderitemView.setProductRetailPrice(orderitem1.getOrderItemProductPrice());
                    orderitemView.calculatePrice();
                    orderitemViewList.add(orderitemView);
                    i += 1;
                }
                map.put("orderitemViewList", orderitemViewList);
                map.put("ordersTotalPrice", orders.getOrdersTotalPrice());
                map.put("isDelete", isDelete);

                return map;
            }
        }

        isDelete = false;

        List<OrderitemView> orderitemViewList = new ArrayList<>();
        int i = 1;
        for(Orderitem orderitem1:orderitemList){
            OrderitemView orderitemView = new OrderitemView();
            orderitemView.setId(Integer.toString(i));
            orderitemView.setProductId(orderitem1.getOrderItemProductId());
            Product product = productMapper.selectById(orderitem1.getOrderItemProductId());
            orderitemView.setProductName(product.getProductName());
            orderitemView.setProductQuantity(orderitem1.getOrderItemProductQuantity());
            orderitemView.setProductRetailPrice(orderitem1.getOrderItemProductPrice());
            orderitemView.calculatePrice();
            orderitemViewList.add(orderitemView);
            i += 1;
        }
        map.put("orderitemViewList", orderitemViewList);
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
    @GetMapping("all")
    public List<Orders> findAllRetail(){
        QueryWrapper<Orders> ordersQueryWrapper=new QueryWrapper<>();
        ordersQueryWrapper.eq("orders_tag",false);
        return ordersMapper.selectList(ordersQueryWrapper);
    }

    /**
     * 进行一定的筛选
     * @param ordersId 订单ID
     * @param customerId 员工ID
     * @param ordersState 订单状态
     * @param repositoryName 仓库名
     * @param staffName 员工名
     * @return 查询之后的列表
     */
    @GetMapping("filter")
    public List<OrdersView> filterOrders(@RequestParam(required = false) Integer ordersId, @RequestParam(required = false) String customerId, @RequestParam(required = false) Integer ordersState, @RequestParam(required = false) String repositoryName, @RequestParam(required = false) String staffName){
//        System.out.println("1"+ordersId+"2"+customerId+"3"+ordersState+"4"+repositoryName+"5"+staffName);

        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        ordersQueryWrapper.eq("orders_tag",false);
        // 订单序号、客户ID、订单状态查询
        if(ordersId!=null)
        {
            ordersQueryWrapper.eq("orders_id", ordersId);
        }
        if(customerId!=null&& !customerId.equals(""))
        {
            ordersQueryWrapper.eq("orders_customer_id", customerId);
        }
        if(ordersState!=null)
        {
            ordersQueryWrapper.eq("orders_state", ordersState);
        }

        // 仓库名
        if(repositoryName!=null &&!repositoryName.equals("")) {
            QueryWrapper<Repository> repositoryQueryWrapper = new QueryWrapper<>();
            repositoryQueryWrapper.eq("repository_name", repositoryName);
            List<Repository> repositoryList = repositoryMapper.selectList(repositoryQueryWrapper);
            Integer ordersRepositoryId = null;
            if (repositoryList.size() == 1) {
                ordersRepositoryId = repositoryList.get(0).getRepositoryId();
            }
            ordersQueryWrapper.eq("orders_repository_id", ordersRepositoryId);
        }

        // 员工名
        if(staffName!=null&&!staffName.equals("")) {
            QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
            staffQueryWrapper.eq("staff_name", staffName);
            List<Staff> staffList = staffMapper.selectList(staffQueryWrapper);
            String ordersStaffId = null;
            if (staffList.size() == 1) {
                ordersStaffId = staffList.get(0).getStaffId();
            }
            ordersQueryWrapper.eq("orders_staff_id", ordersStaffId);
        }

        List<Orders> ordersList=ordersMapper.selectList(ordersQueryWrapper);
        return OrdersView.getOrdersViewList(ordersList,staffMapper);
    }

    /**
     * 根据对应的ordersId找到对应的订单明细
     * @param ordersId 订单ID
     * @return 对应明细
     */
    @GetMapping("get")
    public List<OrderitemView> getSpecificOrderItem(@RequestParam int ordersId){
        QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
        orderitemQueryWrapper.eq("order_item_orders_id", ordersId);
        List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);

        List<OrderitemView> orderitemViewList = new ArrayList<>();
        int i = 0;
        for(Orderitem orderitem1:orderitemList){
            OrderitemView orderitemView = new OrderitemView();
            orderitemView.setId(Integer.toString(i));
            orderitemView.setProductId(orderitem1.getOrderItemProductId());
            Product temp_product = productMapper.selectById(orderitem1.getOrderItemProductId());
            orderitemView.setProductName(temp_product.getProductName());
            orderitemView.setProductQuantity(orderitem1.getOrderItemProductQuantity());
            Product product = productMapper.selectById(orderitem1.getOrderItemProductId());
            orderitemView.setProductRetailPrice(product.getProductRetailPrice());
            orderitemView.calculatePrice();
            orderitemViewList.add(orderitemView);
            i += 1;
        }
        return orderitemViewList;
    }

    /**
     * 退货
     * @param ordersId 传入需要退货的订单号
     * @return 状态码，200表示退货成功，400表示退货失败
     */
    @GetMapping("refund")
    public Result refund(@RequestParam int ordersId) {
        Orders orders = ordersMapper.selectById(ordersId);
        //退货商品入库
        QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
        orderitemQueryWrapper.eq("order_item_orders_id", orders.getOrdersId());
        List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);
        for (Orderitem orderitem : orderitemList){
            Record record = new Record();
            record.setRecordProductId(orderitem.getOrderItemProductId());
            record.setRecordQuantity(orderitem.getOrderItemProductQuantity());
            Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
            record.setRecordTime(timestamp1);
            record.setRecordRepositoryId(orders.getOrdersRepositoryId());
            recordMapper.insert(record);
            RecordController recordController = new RecordController(inventoryMapper);
            recordController.inInventory(orderitem.getOrderItemProductId(), orderitem.getOrderItemProductQuantity(), orders.getOrdersRepositoryId());
        }
        //退款需要支出款
        Account account = new Account();
        account.setAccountAmount(orders.getOrdersTotalPrice().negate());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        account.setAccountTime(timestamp);
        account.setAccountIndex(orders.getOrdersId());
        account.setAccountTag(false); // 零售是false
        accountMapper.insert(account);
        //设置订单状态为退货
        orders.setOrdersState(4);
        ordersMapper.updateById(orders);
        //将退货订单加入aftersale记录中
        Aftersale aftersale = new Aftersale();
        aftersale.setAfterSaleOrdersId(ordersId);
        aftersaleMapper.insert(aftersale);
        return new Result(200);
    }
}
