package br.com.thallysprojetos.desafio2.messaging;

import br.com.thallysprojetos.desafio2.configs.RabbitMQConfig;
import br.com.thallysprojetos.desafio2.events.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishOrderCreated(OrderCreatedEvent event) {
        try {
            log.info("Publishing order created event: orderId={}, total={}", 
                    event.getOrderId(), event.getTotal());
            
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY,
                    event
            );
            
            log.info("Order event published successfully");
        } catch (Exception e) {
            log.error("Error publishing order event: {}", e.getMessage(), e);
        }
    }

}