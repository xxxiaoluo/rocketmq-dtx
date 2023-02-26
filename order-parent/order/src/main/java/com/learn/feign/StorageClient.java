package com.learn.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 远程调用仓库
 */
@FeignClient(name = "STORAGE")
public interface StorageClient {
    @GetMapping( value = "/decrease")
    String decrease(@RequestParam("productId") Long productId,@RequestParam("count") Integer count);
}
