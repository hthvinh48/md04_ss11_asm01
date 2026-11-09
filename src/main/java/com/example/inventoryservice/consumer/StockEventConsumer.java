package com.example.inventoryservice.consumer;

import com.example.inventoryservice.entity.OrderRecord;
import com.example.inventoryservice.event.OrderEvent;
import com.example.inventoryservice.repository.MedicineRepository;
import com.example.inventoryservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockEventConsumer {
    private final MedicineRepository medicineRepository;
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "medicine-stock-events", groupId = "inventory-group")
    @Transactional
    public void handleStockEvent(OrderEvent event) {
        log.info("Nhận sự kiện: orderId={}, medicineId={}, quantity={}",
                event.getOrderId(), event.getMedicineId(), event.getQuantity()
        );

        // Chống xử lý trùng: đơn đã có thì bỏ qua
        if (orderRepository.existsById(event.getOrderId())) {
            log.warn("Đơn hàng đã được xử lý trước đó: orderId={}", event.getOrderId());
            return;
        }

        // Cập nhật số lượng thuốc trong kho
        int updatedRows = medicineRepository.decreaseStock(event.getMedicineId(), event.getQuantity());
        if (updatedRows == 0) {
            log.warn("Không đủ hàng trong kho: medicineId={}, requestedQuantity={}",
                    event.getMedicineId(), event.getQuantity()
            );
            return;
        }

        // Lưu thông tin đơn hàng đã xử lý
        orderRepository.save(new OrderRecord(
                event.getOrderId(),
                event.getMedicineId(),
                event.getQuantity(),
                event.getTimestamp()
        ));

        log.info("Đã thêm đơn {} và trừ kho thuốc {}", event.getOrderId(), event.getMedicineId());
    }
}
