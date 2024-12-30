package com.inventorysystem.inventory_order_system.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventPublisher {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "inventory-updates";

    public void publishEvent(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
