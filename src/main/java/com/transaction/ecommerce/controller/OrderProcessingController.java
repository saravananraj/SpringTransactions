package com.transaction.ecommerce.controller;

import com.transaction.ecommerce.entity.Order;
import com.transaction.ecommerce.handler.InventoryHandler;
import com.transaction.ecommerce.handler.OrderHandler;
import com.transaction.ecommerce.service.OrderProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderProcessingController {

    private final OrderProcessingService orderProcessingService;

    public OrderProcessingController(OrderProcessingService orderProcessingService) {
        this.orderProcessingService = orderProcessingService;
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderProcessingService.placeAnOrder(order));
    }

}
