package com.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.entity.TxInfo;

public interface TxMapper extends BaseMapper<TxInfo> {
    //插入，使用继承的insert()
    //查询，使用继承的selectById()
}
