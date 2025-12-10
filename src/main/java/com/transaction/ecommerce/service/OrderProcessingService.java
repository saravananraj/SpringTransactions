package com.transaction.ecommerce.service;

import com.transaction.ecommerce.entity.Order;
import com.transaction.ecommerce.handler.InventoryHandler;
import com.transaction.ecommerce.handler.OrderHandler;

public class OrderProcessingService {

    private final InventoryHandler inventoryHandler;
    private final OrderHandler orderHandler;

    public  OrderProcessingService(InventoryHandler inventoryHandler, OrderHandler orderHandler) {
        this.inventoryHandler = inventoryHandler;
        this.orderHandler = orderHandler;
    }

    public Order placeOrder(Order order) {
        return order;
    }
}
