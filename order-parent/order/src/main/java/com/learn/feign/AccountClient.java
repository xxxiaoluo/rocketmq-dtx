package com.learn.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * 用过远程调用账户接口
 */
@FeignClient(name = "ACCOUNT")
public interface AccountClient {
    @GetMapping(value = "/decrease")
    String decrease(@RequestParam("userId") Long userId,@RequestParam("money") BigDecimal money);
}
