package com.masai.services;

import com.masai.exception.InvalidStatusTransitionException;
import com.masai.exception.NotFoundException;
import com.masai.model.OrderEntity;
import com.masai.model.OrderStatus;
import com.masai.repositories.OrderRepository;
import com.masai.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public void placeOrder(OrderEntity order) {
        // Implement placeOrder logic
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void assignDelivery(Long orderId, Long deliveryPartnerId) {
        OrderEntity order = getOrderById(orderId);
        if (order == null) {
            throw new NotFoundException("Order not found with ID: " + orderId);
        }

        // Implement assignDelivery logic
        order.setDeliveryPartnerId(deliveryPartnerId);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, String newStatus) {
        OrderEntity order = getOrderById(orderId);
        if (order == null) {
            throw new NotFoundException("Order not found with ID: " + orderId);
        }

        validateStatusTransition(order.getOrderStatus(), newStatus);

        order.setOrderStatus(OrderStatus.valueOf(newStatus)); // Assuming OrderStatus is an enum
        orderRepository.save(order);
    }

    
    public List<OrderEntity> getOrdersByCustomerId(Long customerId) {
        // Implement logic to fetch orders by customerId
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public OrderEntity getOrderById(Long orderId) {
        // Implement logic to fetch order by orderId
        return orderRepository.findById(orderId).orElse(null);
    }

    private void validateStatusTransition(OrderStatus oldStatus, String newStatus) {
        // Implement logic to validate status transition if needed
        // Example: Check if the transition from oldStatus to newStatus is valid
        if (oldStatus.equals(OrderStatus.DELIVERED) && !newStatus.equals(OrderStatus.DELIVERED.name())) {
            throw new InvalidStatusTransitionException("Invalid status transition from " + oldStatus + " to " + newStatus);
        }
    }
}
