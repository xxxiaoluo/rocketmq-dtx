package com.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.entity.Account;

import java.math.BigDecimal;

public interface AccountMapper extends BaseMapper<Account> {
    //扣减账户金额
    void decrease(Long userId, BigDecimal money);
}
