package com.evan.firstspring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.evan.firstspring.bean.Account;
import org.springframework.stereotype.Component;

@Component
public interface AccountMapper extends BaseMapper<Account> {
}