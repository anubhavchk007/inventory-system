package com.inventorysystem.inventory_order_system.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "orders")
@Data
public class Order {

    @Id
    private String id;

    private String userId; // The user placing the order
    private List<OrderItem> items; // List of items in the order
    private double totalPrice; // Total price of the order
    private String status; // e.g., Pending, Shipped, Delivered

    private String createdAt; // Order creation timestamp
    private String updatedAt; // Order last updated timestamp

    // Make OrderItem public to fix accessibility
    @Data
    public static class OrderItem {
        private String inventoryId; // ID of the inventory item
        private int quantity; // Quantity ordered
        private double price; // Price of the item
    }
}
