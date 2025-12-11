package com.transaction.ecommerce.handler;

import com.transaction.ecommerce.entity.Order;
import com.transaction.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderHandler {

    @Autowired
    OrderRepository orderRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
