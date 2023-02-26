package com.learn.controller;

import com.learn.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/decrease")
    public String decrease(Long userId, BigDecimal money){
        accountService.decrease(userId, money);
        return "账户扣减成功";
    }
}
