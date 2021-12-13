package com.evan.firstspring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.Account;
import com.evan.firstspring.bean.Customer;
import com.evan.firstspring.bean.Orders;
import com.evan.firstspring.mapper.*;
import com.evan.firstspring.view.SaleInfoView;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orderitem")
@CrossOrigin
public class OrderItemController {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderitemMapper orderitemMapper;
    @Autowired
    private AccountMapper accountMapper;

    @GetMapping("filter")
    public Map<String, Object> filter(@RequestParam(required = false) String productName, @RequestParam(required = false) String customerName){
        if (customerName != null && customerName.equals("")){
            customerName = null;
        }
        QueryWrapper<Customer> customerQueryWrapper = new QueryWrapper<>();
        customerQueryWrapper.eq("customer_name", customerName);
        List<Customer> customerList = customerMapper.selectList(customerQueryWrapper);
        BigDecimal sum = BigDecimal.valueOf(0);
        for (Customer customer:customerList){
            System.out.println("1");
            QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
            ordersQueryWrapper.eq("orders_customer_id", customer.getCustomerId());
            List<Orders> ordersList = ordersMapper.selectList(ordersQueryWrapper);
            for (Orders orders:ordersList){
                System.out.println("2");
                QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
                accountQueryWrapper.eq("account_index", orders.getOrdersId());
                List<Account> accountList = accountMapper.selectList(accountQueryWrapper);
                for (Account account:accountList){
                    System.out.println("3");
                    sum = sum.add(account.getAccountAmount());
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", SaleInfoView.getSaleInfoViewList(productName, customerName, ordersMapper, orderitemMapper, productMapper, customerMapper));
        map.put("account", sum);
        return map;
    }
}
