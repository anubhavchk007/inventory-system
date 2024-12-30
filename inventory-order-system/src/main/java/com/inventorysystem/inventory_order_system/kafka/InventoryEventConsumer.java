package com.inventorysystem.inventory_order_system.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventConsumer {

    @KafkaListener(topics = "inventory-updates", groupId = "inventory-group")
    public void consumeEvent(String message) {
        System.out.println("Consumed event: " + message);
    }
}
