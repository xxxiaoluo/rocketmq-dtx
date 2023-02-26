package com.learn.controller;

import com.learn.entity.Order;
import com.learn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/create") // ?userId=1&productId=1&count=10&money=100
    public String create(Order order) {
        orderService.create(order);
        return "创建订单成功";
    }

}
