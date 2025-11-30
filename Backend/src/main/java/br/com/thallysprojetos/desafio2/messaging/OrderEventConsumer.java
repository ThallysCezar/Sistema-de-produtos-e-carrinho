package br.com.thallysprojetos.desafio2.messaging;

import br.com.thallysprojetos.desafio2.configs.RabbitMQConfig;
import br.com.thallysprojetos.desafio2.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventConsumer {

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("===== ORDER CREATED EVENT RECEIVED =====");
        log.info("Order ID: {}", event.getOrderId());
        log.info("Total: R$ {}", event.getTotal());
        log.info("Items Count: {}", event.getItemCount());
        log.info("Created At: {}", event.getCreatedAt());
        log.info("========================================");
        
        // Aqui você pode adicionar lógica como:
        // - Enviar email (futuro)
        // - Atualizar dashboard (futuro)
        // - Gerar relatório (futuro)
        // - Notificar sistema externo (futuro)
    }
}
