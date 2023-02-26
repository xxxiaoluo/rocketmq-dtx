package com.learn.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.learn.entity.AccountMessage;
import com.learn.entity.EasyIdReturnResult;
import com.learn.entity.Order;
import com.learn.entity.TxInfo;
import com.learn.feign.AccountClient;
import com.learn.feign.EasyIdClient;
import com.learn.feign.StorageClient;
import com.learn.mapper.OrderMapper;
import com.learn.mapper.TxMapper;
import com.learn.service.OrderService;
import com.learn.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RocketMQTransactionListener
//RocketMQLocalTransactionListener,@RocketMQTransactionListener添加事务监听器
public class OrderServiceImpl implements OrderService, RocketMQLocalTransactionListener {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private EasyIdClient easyIdClient;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private StorageClient storageClient;

    @Autowired
    private RocketMQTemplate t;

    @Autowired
    private TxMapper txMapper;

    public void doCreate(Order order) {
        //远程调用easy-id-generator发号器，生成id
        String s = easyIdClient.nextId("order_business");
        JSONObject jsonObject = JSONObject.parseObject(s);
        EasyIdReturnResult result = jsonObject.toJavaObject(EasyIdReturnResult.class);

        Long id = Long.valueOf(result.getData());

        order.setId(id);
        //创建订单
        orderMapper.create(order);
    }

    /*
    业务方法，不直接完成业务，而是发送事务半消息
    通过发送事务半消息，会触发监听器执行生产者本地业务
     */
    @Override
    public void create(Order order){
        //准备消息数据
        String xid = UUID.randomUUID().toString().replace("-", "");//准备事务ID
        AccountMessage am = new AccountMessage(order.getUserId(),order.getMoney(),xid);
        String json = JsonUtil.to(am);//将消息转成Json
        //将json字符串封装到spring的通用Message对象(不管哪种MQ  spring都支持)
        Message<String> msg = MessageBuilder.withPayload(json).build();//将json转成字节

        //发送事务消息
//        t.sendMessageInTransaction("orderTopic", 消息, 触发监听器执行业务时需要的业务数据参数); //二级标签可以这么写 orderTopic:TagA
        t.sendMessageInTransaction("orderTopic", msg, order); //做两件事：发送半消息到队列;发送生产者要的业务参数执行本地事务
    }


    //执行本地事务
    @Transactional
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {

        log.info("事务监听 - 开始执行本地事务");

        // 监听器中得到的 message payload 是 byte[]
        String json = new String((byte[]) message.getPayload());
        String xid = JsonUtil.getString(json, "xid");

        RocketMQLocalTransactionState state; //用来返回状态
        Integer status; //用来在数据库中保存状态

        try{
            doCreate((Order) o); //本地事务，创建订单
            state = RocketMQLocalTransactionState.COMMIT;
            status = 0;
        }catch(Exception e){
            log.error("创建订单失败",e);
            state = RocketMQLocalTransactionState.ROLLBACK;
            status = 1;
        }
        txMapper.insert(new TxInfo(xid,status,System.currentTimeMillis())); //将生产者(订单模块)事务状态入库
        return state;
    }

    //处理事务状态回查
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        log.info("事务监听 - 回查事务状态");

        /*
        通过半消息中的事务id回查生产者的事务状态
         */
        String json = new String((byte[]) message.getPayload());
        String xid = JsonUtil.getString(json, "xid");

        TxInfo txInfo = txMapper.selectById(xid);
        if(txInfo == null){
            log.info("事务监听 - 回查事务状态 - 事务不存在" + xid);
            return RocketMQLocalTransactionState.UNKNOWN;
        }

        log.info("事务监听 - 回查事务状态 - "+ txInfo.getStatus());

        switch(txInfo.getStatus()){
            case 0: return RocketMQLocalTransactionState.COMMIT;
            case 1: return RocketMQLocalTransactionState.ROLLBACK;
            default: return RocketMQLocalTransactionState.UNKNOWN;
        }
    }
}
