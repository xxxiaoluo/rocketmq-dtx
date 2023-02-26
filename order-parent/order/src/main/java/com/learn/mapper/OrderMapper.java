package com.learn.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.entity.Order;

public interface OrderMapper extends BaseMapper<Order> {
    // 创建订单
    void create(Order order);
}
