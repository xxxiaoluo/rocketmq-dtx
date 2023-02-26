package com.learn.tx;

import com.learn.entity.AccountMessage;
import com.learn.service.AccountService;
import com.learn.util.JsonUtil;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RocketMQMessageListener(consumerGroup = "account_consumer", topic = "orderTopic") //用来接收消息
@Component
public class AccountConsumer implements RocketMQListener<String> { //RocketMQListener<接收的消息类型> :rocketMQ消费者声明

    @Autowired
    private AccountService accountService;

    //处理消息
    @Override
    public void onMessage(String json) { //从服务器接收到的消息是一串字节，会自动转换成字符串
        // json --> AccountMessage
        AccountMessage am = JsonUtil.from(json, AccountMessage.class);
        accountService.decrease(am.getUserId(),am.getMoney());
    }
}
