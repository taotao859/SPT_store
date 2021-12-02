package com.evan.firstspring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.Customer;
import com.evan.firstspring.mapper.CustomerMapper;
import com.evan.firstspring.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerMapper customerMapper;

    @PostMapping("add")
    public Result addCustomer(@RequestBody Customer customer) {
        int i = customerMapper.insert(customer);

        if (i > 0) {
            return new Result(200);
        } else {
            return new Result(400);
        }
    }

    @GetMapping("delete")
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
    public List<Customer> getAllCustomer() {
        return customerMapper.selectList(null);
    }
}
