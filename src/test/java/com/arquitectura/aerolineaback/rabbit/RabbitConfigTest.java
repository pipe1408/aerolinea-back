package com.arquitectura.aerolineaback.rabbit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class RabbitConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testRabbitConfigBeans() {
        DirectExchange exchange = applicationContext.getBean(DirectExchange.class);
        Queue queue = applicationContext.getBean(Queue.class);
        Binding binding = applicationContext.getBean(Binding.class);

        assertNotNull(exchange);
        assertNotNull(queue);
        assertNotNull(binding);

        assertEquals(RabbitConfig.EXCHANGE_NAME, exchange.getName());
        assertEquals(RabbitConfig.QUEUE_NAME, queue.getName());
        assertEquals(RabbitConfig.ROUTING_KEY, binding.getRoutingKey());
    }
}
