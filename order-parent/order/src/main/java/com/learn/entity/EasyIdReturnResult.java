package com.learn.entity;

import lombok.Data;

@Data
public class EasyIdReturnResult {
    private String msg;
    private Boolean success;
    private String data;
}
