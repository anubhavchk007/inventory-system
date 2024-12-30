package com.inventorysystem.inventory_order_system.service;

import com.inventorysystem.inventory_order_system.entity.Inventory;
import com.inventorysystem.inventory_order_system.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryMonitoringService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(fixedRate = 60000)
    public void checkLowStock() {
        List<Inventory> lowStockItems = inventoryRepository.findAll().stream()
                .filter(item -> item.getStock() < item.getLowStockThreshold())
                .toList();

        if (!lowStockItems.isEmpty()) {
            System.out.println("Low stock alert for the following items:");
            lowStockItems.forEach(item -> System.out.println(item.getProductName() + ": " + item.getStock()));
            sendLowStockAlerts(lowStockItems);
        }
    }

    public void sendLowStockAlerts(List<Inventory> lowStockItems) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("sumanhdtu345@gmail.com");
        message.setSubject("Low Stock Items Alert");
        message.setText("The following items have a low stock:\n" +
                lowStockItems.stream()
                        .map(item -> item.getProductName() + ": " + item.getStock())
                        .reduce("", (a, b) -> a + "\n" + b));
        mailSender.send(message);
    }
}
