package com.evan.firstspring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.*;
import com.evan.firstspring.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/accout")
@CrossOrigin
public class AccoutController {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private OrdersMapper ordersMapper;


    /**
     * 筛选时间内的订单和计算对应总和
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 对应的细节
     */
    @GetMapping("sum")
    public Map<String, Object> calculateSum(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate){
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();

        if (startDate != null){
            String startTime = startDate + " 00:00:00";
            accountQueryWrapper.apply("UNIX_TIMESTAMP(tabulation_time) >= UNIX_TIMESTAMP('" + startTime + "')");
        }
        if (endDate != null){
            String endTime = endDate + " 23:59:59";
            accountQueryWrapper.apply("UNIX_TIMESTAMP(tabulation_time) <= UNIX_TIMESTAMP('" + endTime + "')");
        }

        // 查询所有
        List<Account> accountList = accountMapper.selectList(accountQueryWrapper);

        BigDecimal retailSum = BigDecimal.valueOf(0); // 零售收到的钱
        BigDecimal wholeSaleSum = BigDecimal.valueOf(0); // 批发收到的钱
        BigDecimal costSum = BigDecimal.valueOf(0); // 支出的钱
        for(Account account:accountList){
            Orders orders = ordersMapper.selectById(account.getAccountIndex());
            // 支出计算
            if (account.getAccountAmount().compareTo(BigDecimal.valueOf(0)) < 0){
                costSum = costSum.add(account.getAccountAmount());
            }
            // 批发收入
            else if(orders.isOrdersTag()) {
                wholeSaleSum = wholeSaleSum.add(account.getAccountAmount());
            }
            // 零售收入
            else {
                retailSum = retailSum.add(account.getAccountAmount());
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("retail_in", retailSum);
        map.put("wholesale_in", wholeSaleSum);
        map.put("cost_in", costSum);
        map.put("orders_list", accountList);
        return map;
    }
}
