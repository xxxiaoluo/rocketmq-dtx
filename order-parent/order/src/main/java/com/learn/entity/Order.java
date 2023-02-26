package com.learn.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private Long userId;        // 用户id
    private Long productId;     // 商品id
    private Integer count;      // 购买的数量
    private BigDecimal money;   // 花多少钱
    private Integer status;     // 状态，0冻结，1正常
}
