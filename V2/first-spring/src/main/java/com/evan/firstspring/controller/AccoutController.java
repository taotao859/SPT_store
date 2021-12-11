package com.evan.firstspring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.*;
import com.evan.firstspring.mapper.*;
import com.evan.firstspring.view.AccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/account")
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
    public Map<String, Object> calculateSum(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) throws ParseException {
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        if (startDate != null){
            startDate = startDate.replace("Jan", "01");
            startDate = startDate.replace("Feb", "02");
            startDate = startDate.replace("Mar", "03");
            startDate = startDate.replace("Apr", "04");
            startDate = startDate.replace("May", "05");
            startDate = startDate.replace("Jun", "06");
            startDate = startDate.replace("Jul", "07");
            startDate = startDate.replace("Aug", "08");
            startDate = startDate.replace("Sep", "09");
            startDate = startDate.replace("Oct", "10");
            startDate = startDate.replace("Nov", "11");
            startDate = startDate.replace("Dec", "12");
            startDate = startDate.replace("Mon","");
            startDate = startDate.replace("Tue","");
            startDate = startDate.replace("Wed","");
            startDate = startDate.replace("Thu","");
            startDate = startDate.replace("Fri","");
            startDate = startDate.replace("Sat","");
            startDate = startDate.replace("Sun","");
            startDate = startDate.replace(" GMT 0800 (中国标准时间)","");
            DateFormat sdf = new SimpleDateFormat("MM dd yyyy HH:mm:ss");
            DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp startTime = Timestamp.valueOf(sdf2.format(sdf.parse(startDate)));
            System.out.println(startTime);
            accountQueryWrapper.apply("UNIX_TIMESTAMP(account_time) >= UNIX_TIMESTAMP('" + startTime + "')");
        }
        if(endDate != null) {
            endDate = endDate.replace("Jan", "01");
            endDate = endDate.replace("Feb", "02");
            endDate = endDate.replace("Mar", "03");
            endDate = endDate.replace("Apr", "04");
            endDate = endDate.replace("May", "05");
            endDate = endDate.replace("Jun", "06");
            endDate = endDate.replace("Jul", "07");
            endDate = endDate.replace("Aug", "08");
            endDate = endDate.replace("Sep", "09");
            endDate = endDate.replace("Oct", "10");
            endDate = endDate.replace("Nov", "11");
            endDate = endDate.replace("Dec", "12");

            endDate = endDate.replace("Mon ", "");
            endDate = endDate.replace("Tue ", "");
            endDate = endDate.replace("Wed ", "");
            endDate = endDate.replace("Thu ", "");
            endDate = endDate.replace("Fri ", "");
            endDate = endDate.replace("Sat ", "");
            endDate = endDate.replace("Sun ", "");
            startDate = startDate.replace(" GMT 0800 (中国标准时间)","");
            DateFormat sdf = new SimpleDateFormat("MM dd yyyy HH:mm:ss");
            DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp endTime = Timestamp.valueOf(sdf2.format(sdf.parse(endDate)));
            System.out.println(endTime);
            accountQueryWrapper.apply("UNIX_TIMESTAMP(account_time) <= UNIX_TIMESTAMP('" + endTime + "')");
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
        map.put("orders_list", AccountView.getAccountView(accountList));
        return map;
    }

}
