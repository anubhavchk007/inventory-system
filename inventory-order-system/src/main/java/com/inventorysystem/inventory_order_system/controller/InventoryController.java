package com.inventorysystem.inventory_order_system.controller;

import com.inventorysystem.inventory_order_system.entity.Inventory;
import com.inventorysystem.inventory_order_system.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping
    public Inventory addInventory(@Valid @RequestBody Inventory inventory) {
        System.out.println("Received POST request for /inventory with body: " + inventory);
        if (inventory.getLowStockThreshold() == 0) {
            inventory.setLowStockThreshold(10);
        }
        return inventoryService.addInventory(inventory);
    }

    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/paginated")
    public Page<Inventory> getPaginatedInventory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return inventoryService.getPaginatedInventory(page, size);
    }

    @GetMapping("/search")
    public List<Inventory> searchInventory(@RequestParam String productName) {
        return inventoryService.searchInventory(productName);
    }

    @GetMapping("/filter-by-price")
    public List<Inventory> filterInventoryByPrice(
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {
        return inventoryService.filterInventoryByPrice(minPrice, maxPrice);
    }


    @PutMapping("/{id}")
    public Inventory updateInventory(@PathVariable String id,@Valid @RequestBody Inventory inventory) {
        if (inventory.getLowStockThreshold() == 0) {
            inventory.setLowStockThreshold(5);
        }
        return inventoryService.updateInventory(id, inventory);
    }

    @DeleteMapping
    public void deleteAllInventory() {
        inventoryService.deleteAllInventory();
    }

    @DeleteMapping("/{id}")
    public void deleteInventoryById(@PathVariable String id) {
        inventoryService.deleteInventoryById(id);
    }
}
