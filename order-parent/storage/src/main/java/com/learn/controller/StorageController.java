package com.learn.controller;

import com.learn.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController {
    @Autowired
    private StorageService storageService;

    @GetMapping("/decrease")  // ?productId=1&count=10
    public String decrease(Long productId, Integer count) {
        storageService.decrease(productId, count);
        return "减少库存成功";
    }
}
