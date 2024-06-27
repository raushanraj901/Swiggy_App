package com.masai.services;

import com.masai.model.OrderEntity;

import java.util.List;

public interface OrderService {

    void placeOrder(OrderEntity order);

    void assignDelivery(Long orderId, Long deliveryPartnerId);

    void updateOrderStatus(Long orderId, String newStatus);

    List<OrderEntity> getOrdersByCustomerId(Long customerId);

    OrderEntity getOrderById(Long orderId);
}
