package com.transaction.ecommerce.service;

import com.transaction.ecommerce.entity.Order;
import com.transaction.ecommerce.entity.Product;
import com.transaction.ecommerce.handler.InventoryHandler;
import com.transaction.ecommerce.handler.OrderHandler;
import org.springframework.stereotype.Service;

@Service
public class OrderProcessingService {

    private final InventoryHandler inventoryHandler;
    private final OrderHandler orderHandler;

    public  OrderProcessingService(InventoryHandler inventoryHandler, OrderHandler orderHandler) {
        this.inventoryHandler = inventoryHandler;
        this.orderHandler = orderHandler;
    }

    public Order placeAnOrder(Order order) {

        // fetch the product details
        Product product = inventoryHandler.getProduct(order.getProductId());

        validateOrder(order, product);

        // update the total price and persist order

        order.setTotalPrice(order.getQuantity() * product.getPrice());
        orderHandler.saveOrder(order);

        // update the inventory

        UpdateInventory(order, product);

        return order;
    }

    private static void validateOrder(Order order, Product product) {
        // check the availability
        if (product.getStockQuantity() < order.getQuantity()) {
            throw new RuntimeException("Product available is less than ordered quantity");
        }
    }

    private void UpdateInventory(Order order, Product product) {
        int availableStock = product.getStockQuantity() - order.getQuantity();
        product.setStockQuantity(availableStock);
        inventoryHandler.updateProductDetails(product);
    }
}
