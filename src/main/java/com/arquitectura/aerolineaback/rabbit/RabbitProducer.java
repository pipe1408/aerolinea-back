package com.arquitectura.aerolineaback.rabbit;

import com.arquitectura.aerolineaback.logica.dto.UpdateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(UpdateDTO updateDTO) {

        try {
            String json = objectMapper.writeValueAsString(updateDTO);

            rabbitTemplate.convertAndSend(
                    RabbitConfig.EXCHANGE_NAME,
                    RabbitConfig.ROUTING_KEY,
                    json
            );

            log.info("Update sent: {}", json);
        } catch (JsonProcessingException jpe) {
            log.error(jpe.getMessage());
        }
    }
}
