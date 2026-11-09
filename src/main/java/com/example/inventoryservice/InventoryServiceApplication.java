package com.example.inventoryservice;

import com.example.inventoryservice.entity.Medicine;
import com.example.inventoryservice.repository.MedicineRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner seed(MedicineRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Medicine("PARA-500", "Paracetamol 500mg", 100));
                repo.save(new Medicine("AMOX-250", "Amoxicillin 250mg", 50));
            }
        };
    }
}
