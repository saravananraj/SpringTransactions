package com.transaction.ecommerce.handler;

import com.transaction.ecommerce.entity.Order;
import com.transaction.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderHandler {

    @Autowired
    OrderRepository orderRepository;

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
