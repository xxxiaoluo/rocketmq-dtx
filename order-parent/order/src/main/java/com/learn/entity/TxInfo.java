package com.learn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxInfo {
    private String xid; //事务id
    private Integer status; //事务执行状态 0成功 1失败 2未知
    private Long created; //存储时间

}
