package com.learn.service.Impl;

import com.learn.mapper.AccountMapper;
import com.learn.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void decrease(Long userId, BigDecimal money) {
        accountMapper.decrease(userId, money);
    }
}
