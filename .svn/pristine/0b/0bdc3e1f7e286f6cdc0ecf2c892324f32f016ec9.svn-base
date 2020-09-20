package com.feather.smart.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class rabbitmqConfig {
    public static final String QUEUE_INFORM_SB="queue_inform_sb";    //设备队列

    public static final String EXCHANGE_TOPICS_INFORM="exchange_topics_inform";        //交换机

    public static final String ROUTINGKEY_SB="inform.#.sb.#";        //sms通配符路由key


    //1.声明交换机
    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange EXCHANGE_TOPICS_INFORM(){
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }


    //2.声明队列
    @Bean(QUEUE_INFORM_SB)
    public Queue QUEUE_INFORM_SB(){
        return new Queue(QUEUE_INFORM_SB);
    }



    //3.队列绑定交换机

    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(QUEUE_INFORM_SB) Queue queue, @Qualifier(EXCHANGE_TOPICS_INFORM)Exchange exchange){
        return  BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_SB).noargs();
    }

}
