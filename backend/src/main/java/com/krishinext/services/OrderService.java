package com.krishinext.services;

import com.krishinext.models.Order;
import com.krishinext.models.Crop;
import com.krishinext.repositories.OrderRepository;
import com.krishinext.repositories.CropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CropRepository cropRepository;

    @Transactional
    public Order placeOrder(Order order) {
        Crop crop = cropRepository.findById(order.getCrop().getId())
                .orElseThrow(() -> new RuntimeException("Crop not found"));

        if (crop.getQuantity() < order.getOrderQty()) {
            throw new RuntimeException("Insufficient stock");
        }

        // Decrement stock
        crop.setQuantity(crop.getQuantity() - order.getOrderQty());
        cropRepository.save(crop);

        return orderRepository.save(order);
    }

    public List<Order> getOrdersBySeller(String sellerId) {
        // Implementation for retrieving orders for a specific seller
        return null; // Placeholder for actual implementation
    }
}
