package com.evan.firstspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.evan.firstspring.bean.Inventory;
import org.springframework.stereotype.Component;

@Component
public interface InventoryMapper extends BaseMapper<Inventory> {
}