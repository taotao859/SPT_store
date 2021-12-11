package com.evan.firstspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.evan.firstspring.bean.Orders;
import org.springframework.stereotype.Component;

@Component
public interface OrdersMapper extends BaseMapper<Orders> {
}