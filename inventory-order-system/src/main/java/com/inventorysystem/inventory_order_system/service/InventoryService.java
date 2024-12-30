package com.inventorysystem.inventory_order_system.service;

import com.inventorysystem.inventory_order_system.entity.Inventory;
import com.inventorysystem.inventory_order_system.kafka.InventoryEventPublisher;
import com.inventorysystem.inventory_order_system.repository.InventoryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryEventPublisher inventoryEventPublisher;

    public Inventory addInventory(Inventory inventory) {
        Inventory savedInventory = inventoryRepository.save(inventory);
        inventoryEventPublisher.publishEvent("Added inventory: " + savedInventory);
        return savedInventory;
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory updateInventory(String id, Inventory updatedInventory) {
        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found"));
        existingInventory.setProductName(updatedInventory.getProductName());
        existingInventory.setStock(updatedInventory.getStock());
        existingInventory.setPrice(updatedInventory.getPrice());

        return inventoryRepository.save(updatedInventory);
    }

    public void deleteAllInventory() {
        inventoryRepository.deleteAll();
    }

    public void deleteInventoryById(String id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found"));
        inventoryRepository.delete(inventory);
    }

    public void checkLowStock(Inventory inventory) {
        if (inventory.getStock() < inventory.getLowStockThreshold()) {
            System.out.println("Low stock alert for product " + inventory.getProductName() +
                    ". Current stock: " + inventory.getStock());
        }
    }

    public Page<Inventory> getPaginatedInventory(int page, int size) {
        return inventoryRepository.findAll(PageRequest.of(page, size));
    }

    public List<Inventory> searchInventory(String productName) {
        return inventoryRepository.findByProductNameContainingIgnoreCase(productName);
    }

    public List<Inventory> filterInventoryByPrice(double minPrice, double maxPrice) {
        return inventoryRepository.findByPriceBetween(minPrice, maxPrice);
    }
}
