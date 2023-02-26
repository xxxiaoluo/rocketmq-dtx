package com.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.entity.Storage;

public interface StorageMapper extends BaseMapper<Storage> {
    //减少库存
    void decrease(Long productId,Integer count);
}
