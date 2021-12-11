package com.evan.firstspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.evan.firstspring.bean.Product;
import org.springframework.stereotype.Component;

@Component
public interface ProductMapper extends BaseMapper<Product> {
}