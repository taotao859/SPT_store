package com.evan.firstspring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.Customer;
import com.evan.firstspring.mapper.CustomerMapper;
import com.evan.firstspring.result.Result;
import com.evan.firstspring.view.CustomerView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerMapper customerMapper;

    @PostMapping("add")
    public Result addCustomer(@RequestBody Customer customer) {
        List<Customer> customerList = customerMapper.selectList(null);
        Integer integer = customerList.size() + 1;
        customer.setCustomerId(integer.toString());
        // password在这里修改
        customer.setCustomerPassword("1234");
        int i = customerMapper.insert(customer);

        if (i > 0) {
            return new Result(200);
        } else {
            return new Result(400);
        }
    }

    @PostMapping("delete")
    public Result deleteCustomer(@RequestBody Customer customer) {
        int i = customerMapper.deleteById(customer.getCustomerId());

        if (i > 0) {
            return new Result(200);
        } else {
            return new Result(400);
        }
    }

    @PostMapping("update")
    public Result updateCustomer(@RequestBody Customer customer) {
        int i = customerMapper.updateById(customer);

        if (i > 0) {
            return new Result(200);
        } else {
            return new Result(400);
        }
    }

    @GetMapping("search")
    public List<Customer> searchCustomer(@RequestParam String customerName) {
        QueryWrapper<Customer> customerQueryWrapper = new QueryWrapper<>();
        customerQueryWrapper.eq("customer_name", customerName);

        return customerMapper.selectList(customerQueryWrapper);
    }

    @GetMapping("all")
    public List<CustomerView> getAllCustomer() {
        return CustomerView.getCustomerView(customerMapper.selectList(null));
    }

    @GetMapping("get")
    public Map<String,Object> getSpecificCustomer(@RequestParam String customerId){
        Customer customer=customerMapper.selectById(customerId);
        Map<String,Object> retMap=new HashMap<>();
        retMap.put("name",customer.getCustomerName());
        String title;
        if(!customer.getCustomerTag())
        {
            title="零售客户";
        }else
        {
            title="批发客户";
        }
        retMap.put("tag",title);
        retMap.put("contact",customer.getCustomerContact());
        retMap.put("score",customer.getCustomerScore());
        return retMap;
    }
}
