package com.learn.service.Impl;

import com.learn.mapper.StorageMapper;
import com.learn.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageMapper storageMapper;

    @Override
    public void decrease(Long productId, Integer count) {
        storageMapper.decrease(productId, count);
    }
}
