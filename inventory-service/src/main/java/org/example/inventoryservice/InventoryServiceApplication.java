package org.example.inventoryservice;

import org.example.inventoryservice.entity.Inventory;
import org.example.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
        return (args) -> {
            inventoryRepository.save(new Inventory(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE, "A", 10));
            inventoryRepository.save(new Inventory(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE, "B", 20));
        };
    }
}
