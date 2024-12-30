package com.inventorysystem.inventory_order_system.controller;

import com.inventorysystem.inventory_order_system.entity.Order;
import com.inventorysystem.inventory_order_system.entity.Order.OrderItem; // Fix the import
import com.inventorysystem.inventory_order_system.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@AuthenticationPrincipal User user, @RequestBody List<OrderItem> items) {
        return orderService.createOrder(user.getUsername(), items);
    }

    @GetMapping
    public List<Order> getAllOrders(@AuthenticationPrincipal User user) {
        // Allow admins to view all orders
        if (user.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return orderService.getAllOrders();
        }
        // Otherwise, return only the user's orders
        return orderService.getUserOrders(user.getUsername());
    }

    @PutMapping("/{orderId}/status")
    public Order updateOrderStatus(@PathVariable String orderId, @RequestParam String status) {
        return orderService.updateOrderStatus(orderId, status);
    }
}
