package com.inventorysystem.inventory_order_system.entity;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "inventory")
@Data
public class Inventory {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    @Min(value = 0, message = "Stock must be non-negative")
    private int stock;

    @Min(value = 0, message = "Price must be non-negative")
    private double price;

    @Min(value = 0, message = "Minimum stock must be non-negative")
    private int lowStockThreshold;
}
