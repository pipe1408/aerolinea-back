package com.arquitectura.aerolineaback.rabbit;

import static org.mockito.Mockito.*;

import com.arquitectura.aerolineaback.logica.dto.UpdateDTO;
import com.arquitectura.aerolineaback.logica.EstadoEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDate;
import java.time.LocalTime;

@ExtendWith(MockitoExtension.class)
class RabbitProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private RabbitProducer rabbitProducer;

    @Test
    void testSendMessage() throws Exception {
        // Prepare data with correct types
        LocalDate fecha = LocalDate.of(2024, 11, 22);
        LocalTime hora = LocalTime.of(12, 0);
        EstadoEnum previousState = EstadoEnum.PROGRAMADO;
        EstadoEnum newState = EstadoEnum.EN_VUELO;

        UpdateDTO updateDTO = new UpdateDTO("123", "A", "B", fecha, hora, previousState, newState);

        String json = "{\"flightId\":\"123\",\"origen\":\"A\",\"destino\":\"B\",\"fecha\":\"2024-11-22\",\"hora\":\"12:00\",\"previousState\":\"PROGRAMADO\",\"newState\":\"EN_VUELO\"}";

        when(objectMapper.writeValueAsString(updateDTO)).thenReturn(json);

        rabbitProducer.sendMessage(updateDTO);

        verify(objectMapper, times(1)).writeValueAsString(updateDTO);
        verify(rabbitTemplate, times(1)).convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUTING_KEY,
                json
        );
    }
}
