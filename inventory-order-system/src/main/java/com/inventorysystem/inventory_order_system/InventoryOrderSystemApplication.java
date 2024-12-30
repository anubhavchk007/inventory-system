package com.inventorysystem.inventory_order_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InventoryOrderSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryOrderSystemApplication.class, args);
	}

}
