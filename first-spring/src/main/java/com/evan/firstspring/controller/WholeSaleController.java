package com.evan.firstspring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.*;
import com.evan.firstspring.mapper.*;
import com.evan.firstspring.result.Result;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/wholesale")
@CrossOrigin
public class WholeSaleController {
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
    private RepositoryMapper repositoryMapper;
    @Autowired
    private StaffMapper staffMapper;

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
        orders.setOrdersTag(true);
        int i = ordersMapper.insert(orders);
        if (i > 0) {
            return orders;
        } else {
            return null;
        }
    }


    /**
     * 向订单添加新项，相较于零售参数中会多一个返回的批发价格
     *
     * @param productId       新添的订单表项货品ID
     * @param productQuantity 新添的订单表项货品数量
     * @param productPrice 新添的订单表项货品批发价格
     * @return 一个包含两个元素的map，第一个元素的key为“订单”，value为orderitemList;第二个元素的key为“总价”，value为totalPrice
     */
    @PostMapping("add")
    public Map<String, Object> addOrderitem(@RequestParam int productId, @RequestParam int productQuantity, @RequestParam BigDecimal productPrice, @RequestBody Orders orders) {
        QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
        orderitemQueryWrapper.eq("order_item_orders_id", orders.getOrdersId());
        List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);
        List<BigDecimal> totalPriceList = new ArrayList<>(); //总价
        Map<String, Object> map = new HashMap<>(); //第一个map元素是订单，第二个map元素是总价

        // 计算总价
        orders.setOrdersTotalPrice(orders.getOrdersTotalPrice().add(productPrice.multiply(BigDecimal.valueOf(productQuantity))));
        // 计算利润
        Product product = productMapper.selectById(productId);
        BigDecimal productProfit = productPrice.subtract(product.getProductCost());
        orders.setOrdersProfit(orders.getOrdersProfit().add(productProfit.multiply(BigDecimal.valueOf(productQuantity))));
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
        orderitem.setOrderItemProductPrice(productPrice);

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

    // 删除单个货品
    @PostMapping("delete")
    public Map<String, Object> deleteOrderItem(@RequestParam int productId, @RequestParam int productQuantity, @RequestParam BigDecimal productPrice, @RequestBody Orders orders){
        QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
        orderitemQueryWrapper.eq("order_item_orders_id", orders.getOrdersId());
        List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);
        List<BigDecimal> totalPriceList = new ArrayList<>(); //总价
        Boolean isDelete = true;
        Map<String, Object> map = new HashMap<>(); //第一个map元素是订单，第二个map元素是总价

        // 在OrderItemList中找到对应商品
        for (Orderitem orderitem : orderitemList) {
            if (orderitem.getOrderItemProductId() == productId) {
                int numberLeft = orderitem.getOrderItemProductQuantity() - productQuantity;
                if(numberLeft > 0){
                    // 计算总价
                    orders.setOrdersTotalPrice(orders.getOrdersTotalPrice().subtract(productPrice.multiply(BigDecimal.valueOf(productQuantity))));

                    // 计算利润
                    Product product = productMapper.selectById(productId);
                    BigDecimal productProfit = productPrice.subtract(product.getProductCost());
                    orders.setOrdersProfit(orders.getOrdersProfit().subtract(productProfit.multiply(BigDecimal.valueOf(productQuantity))));
                    ordersMapper.updateById(orders);
                    // 删除列表元素
                    orderitem.setOrderItemProductQuantity(numberLeft);
                    orderitemMapper.updateById(orderitem);
                }
                else{
                    orders.setOrdersTotalPrice(orders.getOrdersTotalPrice().subtract(productPrice.multiply(BigDecimal.valueOf(orderitem.getOrderItemProductQuantity()))));
                    // 计算利润
                    Product product = productMapper.selectById(productId);
                    BigDecimal productProfit = productPrice.subtract(product.getProductCost());
                    orders.setOrdersProfit(orders.getOrdersProfit().subtract(productProfit.multiply(BigDecimal.valueOf(productQuantity))));

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

    /**
     *
     * @return 所有批发用户
     */
    @GetMapping("all")
    public List<Orders> findAllWholeSale(){
        QueryWrapper<Orders> ordersQueryWrapper=new QueryWrapper<>();
        ordersQueryWrapper.eq("ordesTag",true);
        return ordersMapper.selectList(ordersQueryWrapper);
    }

    /**
     * 审核
     * @param orders 传入订单
     * @return 200代表够得，400不够，审核不过
     */
    @PostMapping("check")
    public Result checkOrder(@RequestBody Orders orders){
        QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
        orderitemQueryWrapper.eq("order_item_orders_id", orders.getOrdersId());
        List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);
        for(Orderitem orderitem:orderitemList){
            RecordController recordController = new RecordController();
            if (recordController.GetThisInventoryNumber(orderitem.getOrderItemOrdersId(), orderitem.getOrderItemProductQuantity(), orders.getOrdersRepositoryId())){
                continue;
            }
            else {
                return new Result(400);
            }
        }
        return new Result(200);
    }

    /**
     * 付款
     * @param orders 待付款订单
     * @param paidMoney 此次付款金额
     * @return 付款状态的map
     */
    @PostMapping("pay")
    public Map<String, BigDecimal> payOrder(@RequestBody Orders orders, @RequestParam BigDecimal paidMoney){
        Map<String, BigDecimal> map = new HashMap<>();
        // 查询先前已付金额
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("account_index", orders.getOrdersId());
        List<Account> accountList = accountMapper.selectList(accountQueryWrapper);
        // 计算带订单所有金额，已付金额，代付金额
        BigDecimal paidTotal = BigDecimal.valueOf(0);
        for(Account account:accountList){
            paidTotal = paidTotal.add(account.getAccountAmount());
        }
        // 此时已付金额大于等于订单总金额，且默认只有当付完款时才会更改库存
        if(paidTotal.add(paidMoney).compareTo(orders.getOrdersTotalPrice()) >= 0){
            // 收支表变化
            Account account = new Account();
            account.setAccountAmount(orders.getOrdersTotalPrice().subtract(paidTotal));
            account.setAccountTime(new DateTime());
            account.setAccountIndex(orders.getOrdersId());
            account.setAccountTag(true); // 批发是false
            accountMapper.insert(account);
            // record记录增加和库存改变
            QueryWrapper<Orderitem> orderitemQueryWrapper = new QueryWrapper<>();
            orderitemQueryWrapper.eq("order_item_orders_id", orders.getOrdersId());
            List<Orderitem> orderitemList = orderitemMapper.selectList(orderitemQueryWrapper);
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
            // 更改订单状态
            orders.setOrdersState(3); //订单状态为已付款
            ordersMapper.updateById(orders);
            //返回收款信息
            map.put("ordersTotalPrice", orders.getOrdersTotalPrice());
            map.put("ordersPaidAmount", orders.getOrdersTotalPrice());
            map.put("ordersUnpaidAmount", BigDecimal.valueOf(0));
            return map;
        }
        // 收支表变化
        Account account = new Account();
        account.setAccountAmount(paidMoney);
        account.setAccountTime(new DateTime());
        account.setAccountIndex(orders.getOrdersId());
        account.setAccountTag(true); // 批发是false
        accountMapper.insert(account);
        // 更改订单状态
        orders.setOrdersState(2); //订单状态为待付款
        ordersMapper.updateById(orders);
        // 返回收款信息
        map.put("ordersTotalPrice", orders.getOrdersTotalPrice());
        map.put("ordersPaidAmount", paidTotal.add(paidMoney));
        map.put("ordersUnpaidAmount", orders.getOrdersTotalPrice().subtract(paidTotal.add(paidMoney)));
        return map;
    }

    /**
     * 得到对应下拉框内容
     * @return 返回map对象
     */
    @GetMapping("options")
    public Map<String, Object> filerOptions(){
        Map<String, Object> map = new HashMap<>();
        // 加入订单状态
        List<String> stateList = new ArrayList<>();
        stateList.add("未审核");
        stateList.add("审核不通过");
        stateList.add("待付款");
        stateList.add("已付款");
        map.put("ordersStatusList", stateList);

        // 加入仓库名
        List<Repository> repositoryList = repositoryMapper.selectList(null);
        Set<String> repositoryName = new HashSet<>();
        for (Repository repository:repositoryList){
            repositoryName.add(repository.getRepositoryName());
        }
        List<String> repository = new ArrayList<>(repositoryName);
        map.put("repositoryNameList", repository);

        // 加入员工名
        List<Staff> staffList = staffMapper.selectList(null);
        Set<String> staffName = new HashSet<>();
        for (Staff staff:staffList){
            staffName.add(staff.getStaffName());
        }
        List<String> staff = new ArrayList<>(staffName);
        map.put("staffNameList", staff);
        return map;
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
    public List<Orders> filterOrders(@RequestParam(required = false) int ordersId, @RequestParam(required = false) String customerId, @RequestParam(required = false) int ordersState, @RequestParam(required = false) String repositoryName, @RequestParam(required = false) String staffName){
        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        // 订单序号、客户ID、订单状态查询
        ordersQueryWrapper.eq("orders_id", ordersId);
        ordersQueryWrapper.eq("orders_customer_id", customerId);
        ordersQueryWrapper.eq("orders_state", ordersState);

        // 仓库名
        QueryWrapper<Repository> repositoryQueryWrapper = new QueryWrapper<>();
        repositoryQueryWrapper.eq("repository_name", repositoryName);
        List<Repository> repositoryList = repositoryMapper.selectList(repositoryQueryWrapper);
        Integer ordersRepositoryId = null;
        if(repositoryList.size() == 1){
            ordersRepositoryId = repositoryList.get(0).getRepositoryId();
        }
        ordersQueryWrapper.eq("orders_repository_id", ordersRepositoryId);

        // 员工名
        QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
        staffQueryWrapper.eq("staff_name", staffName);
        List<Staff> staffList = staffMapper.selectList(staffQueryWrapper);
        String ordersStaffId = null;
        if(staffList.size() == 1){
            ordersStaffId = staffList.get(0).getStaffId();
        }
        ordersQueryWrapper.eq("orders_staff_id", ordersStaffId);

        return ordersMapper.selectList(ordersQueryWrapper);
    }
}
