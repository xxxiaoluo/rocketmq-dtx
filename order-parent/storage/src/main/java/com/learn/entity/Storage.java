package com.learn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Storage {
    private Long id;
    private Long productId; //商品id
    private Integer total;  //总数
    private Integer used;  //已使用、已出售
    private Integer residue;  //可用库存
    private Integer frozen;  //冻结库存
}
