package com.feather.smart.mq;


import com.feather.common.core.domain.AjaxResult;
import com.feather.smart.config.rabbitmqConfig;
import com.feather.smart.service.IZhsqZnsbService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ReceiveHandle {

    @Autowired
    IZhsqZnsbService iZhsqZnsbService;

    @RabbitListener(queues = {rabbitmqConfig.QUEUE_INFORM_SB})
    public AjaxResult getMessage(Map map, Message message, Channel channel){
        String name = map.get("name").toString();
        String requestBody = map.get("requestBody").toString();
         return iZhsqZnsbService.saveSb(name, requestBody);
    }
}
