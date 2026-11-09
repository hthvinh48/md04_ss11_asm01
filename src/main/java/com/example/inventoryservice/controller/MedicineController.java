package com.example.inventoryservice.controller;

import com.example.inventoryservice.entity.Medicine;
import com.example.inventoryservice.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @GetMapping("/{id}")
    public Medicine getById(@PathVariable String id) {
        return medicineService.getById(id);
    }
}