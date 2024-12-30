package com.inventorysystem.inventory_order_system.service;

import com.inventorysystem.inventory_order_system.entity.Inventory;
import com.inventorysystem.inventory_order_system.entity.Order;
import com.inventorysystem.inventory_order_system.entity.Order.OrderItem;
import com.inventorysystem.inventory_order_system.repository.InventoryRepository;
import com.inventorysystem.inventory_order_system.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public Order createOrder(String userId, List<OrderItem> items) {
        double totalPrice = 0;

        for (OrderItem item : items) {
            Optional<Inventory> inventoryOpt = inventoryRepository.findById(item.getInventoryId());
            if (inventoryOpt.isEmpty()) {
                throw new RuntimeException("Inventory item not found: " + item.getInventoryId());
            }

            Inventory inventory = inventoryOpt.get();
            if (inventory.getStock() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for item: " + inventory.getProductName());
            }

            inventory.setStock(inventory.getStock() - item.getQuantity());
            inventoryRepository.save(inventory);

            item.setPrice(inventory.getPrice() * item.getQuantity());
            totalPrice += item.getPrice();
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setItems(items);
        order.setTotalPrice(totalPrice);
        order.setStatus("Pending");
        order.setCreatedAt(LocalDateTime.now().toString());
        order.setUpdatedAt(LocalDateTime.now().toString());

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getUserOrders(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order updateOrderStatus(String orderId, String status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found: " + orderId);
        }

        Order order = orderOpt.get();
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now().toString());
        return orderRepository.save(order);
    }

}
