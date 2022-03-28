package com.catalouge.book.bookcatalouge.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

@EnableJms
@Configuration
public class ActiveMQConfig {

    @Bean
    public Queue createQueue(){
        return new ActiveMQQueue("book.events.queue");
    }
}
