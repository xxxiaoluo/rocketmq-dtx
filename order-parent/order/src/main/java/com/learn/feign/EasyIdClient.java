package com.learn.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 获取id
 */
@FeignClient(name = "EASY-ID") //调哪个服务
public interface EasyIdClient {
    @GetMapping("/segment/ids/next_id")
    String nextId(@RequestParam("businessType") String businessType);
}
