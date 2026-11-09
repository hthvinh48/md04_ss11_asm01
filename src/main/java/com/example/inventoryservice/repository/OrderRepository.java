package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderRecord, String> {
}
