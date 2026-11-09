package com.example.inventoryservice.repository;

import com.example.inventoryservice.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicineRepository extends JpaRepository<Medicine, String> {
    // UPDATE medicines SET stock = stock - quantity (chỉ khi đủ hàng)
    @Modifying
    @Query("update Medicine m set m.stock = m.stock - :qty where m.id = :id and m.stock >= :qty")
    int decreaseStock(@Param("id") String id, @Param("qty") int qty);
}
