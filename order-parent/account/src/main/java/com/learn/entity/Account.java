package com.learn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;

    //用户id
    private Long userId;

    //总数
    private BigDecimal total;

    //已使用、已消费金额
    private BigDecimal used;

    //可用金额
    private BigDecimal residue;

    //冻结金额
    private BigDecimal frozen;
}
