package com.inventorysystem.inventory_order_system.repository;

import com.inventorysystem.inventory_order_system.entity.Inventory;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
    Page<Inventory> findAll(Pageable pageable);

    List<Inventory> findByProductNameContainingIgnoreCase(String productName);

    List<Inventory> findByPriceBetween(double minPrice, double maxPrice);
}
