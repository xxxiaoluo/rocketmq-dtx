package com.learn.service;

import java.math.BigDecimal;

public interface AccountService {
    //扣减账户
    void decrease(Long userId, BigDecimal money);
}
